package com.order.service.implementations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.order.clients.PaymentClient;
import com.order.clients.ProductClient;
import com.order.clients.UserClient;
import com.order.dto.NotificationEvent;
import com.order.dto.requests.OrderItemRequest;
import com.order.dto.requests.OrderRequest;
import com.order.dto.requests.PaymentRequest;
import com.order.dto.responses.OrderResponse;
import com.order.dto.responses.PaymentResponse;
import com.order.dto.responses.ProductResponse;
import com.order.dto.responses.UserResponse;
import com.order.entity.Order;
import com.order.entity.OrderItem;
import com.order.entity.Shipping;
import com.order.enums.NotificationChannel;
import com.order.enums.NotificationType;
import com.order.enums.OrderStatus;
import com.order.enums.PaymentStatus;
import com.order.exception.PaymentFailedException;
import com.order.exception.ResourceNotFoundException;
import com.order.mappers.OrderMapper;
import com.order.mappers.UUIDMapper;
import com.order.repository.OrderRepository;
import com.order.service.abstractions.OrderService;
import com.order.service.producers.NotificationProducer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserClient userServiceClient;
    private final ProductClient productClient;
    private final NotificationProducer notificationProducer;
    private final PaymentClient paymentClient;
    private OrderMapper orderMapper;
    private UUIDMapper uuidMapper;

    @Override
    public OrderResponse placeOrder(OrderRequest request) {
        UserResponse user = fetchUser(uuidMapper.toUUID(request.getUserId()));

        List<ProductResponse> products = fetchProducts(request);
        Map<String, ProductResponse> productMap = mapProductsById(products);

        List<OrderItem> orderItems = buildOrderItems(request, productMap);
        BigDecimal totalPrice = calculateTotalPrice(orderItems);

        Order order = createOrder(user, request, orderItems, totalPrice);
        Order savedOrder = orderRepository.save(order);

        sendOrderConfirmationNotification(user, savedOrder);

        processPaymentOrThrow(request, savedOrder, user, totalPrice);

        sendPaymentSuccessNotification(user, savedOrder);

        return orderMapper.toDto(savedOrder);
    }

    private UserResponse fetchUser(UUID userId) {
        UserResponse user = userServiceClient.getUserById(userId.toString());
        if (user == null) {
            throw new ResourceNotFoundException("User", "id", userId);
        }
        return user;
    }

    private List<ProductResponse> fetchProducts(OrderRequest request) {
        List<String> productIds = request.getOrderItems().stream()
                .map(OrderItemRequest::getProductId)
                .toList();

        List<ProductResponse> products = productClient.getProductsByIdList(productIds);
        if (products == null || products.isEmpty()) {
            throw new ResourceNotFoundException("Products", "ids", productIds);
        }
        return products;
    }

    private Map<String, ProductResponse> mapProductsById(List<ProductResponse> products) {
        return products.stream()
                .collect(Collectors.toMap(ProductResponse::getId, p -> p));
    }

    private List<OrderItem> buildOrderItems(OrderRequest request, Map<String, ProductResponse> productMap) {
        return request.getOrderItems().stream()
                .map(orderItemRequest -> {
                    ProductResponse product = productMap.get(orderItemRequest.getProductId());
                    if (product == null) {
                        throw new ResourceNotFoundException("Product", "id", orderItemRequest.getProductId());
                    }
                    OrderItem item = new OrderItem();
                    item.setProductId(product.getId());
                    item.setProductName(product.getName());
                    item.setPrice(product.getPrice());
                    item.setQuantity(orderItemRequest.getQuantity());
                    return item;
                }).toList();
    }

    private BigDecimal calculateTotalPrice(List<OrderItem> items) {
        return items.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Order createOrder(UserResponse user, OrderRequest request, List<OrderItem> items, BigDecimal totalPrice) {
        Order order = new Order();
        order.setUserId(uuidMapper.toUUID(user.getId()));
        order.setTotalAmount(totalPrice);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setCreatedAt(LocalDateTime.now());
        items.forEach(item -> item.setOrder(order));
        order.setOrderItems(items);

        Shipping shipping = new Shipping();
        shipping.setAddress(request.getShipping().getAddress());
        shipping.setCity(request.getShipping().getCity());
        shipping.setCountry(request.getShipping().getCountry());
        shipping.setPostalCode(request.getShipping().getPostalCode());
        order.setShipping(shipping);

        return order;
    }

    private void sendOrderConfirmationNotification(UserResponse user, Order order) {
        NotificationEvent event = new NotificationEvent(
                user.getId(),
                "Order Confirmed",
                "Order Confirmation",
                "Your order #" + order.getId() + " has been confirmed successfully!",
                NotificationType.ORDER_CONFIRMATION,
                NotificationChannel.EMAIL);
        notificationProducer.sendNotification(event);
    }

    private void processPaymentOrThrow(OrderRequest request, Order savedOrder, UserResponse user, BigDecimal amount) {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setOrderId(uuidMapper.toString(savedOrder.getId()));
        paymentRequest.setAmount(amount);
        paymentRequest.setPaymentMethod(request.getPayment().getPaymentMethod());

        PaymentResponse paymentResponse = paymentClient.processPayment(paymentRequest);

        if (paymentResponse == null || paymentResponse.getStatus() != PaymentStatus.SUCCESS) {
            NotificationEvent failure = new NotificationEvent(
                    user.getId(),
                    "Payment failed",
                    "Payment Failed",
                    "Your payment for order #" + savedOrder.getId() + " has failed.",
                    NotificationType.PAYMENT_FAILED,
                    NotificationChannel.EMAIL);
            notificationProducer.sendNotification(failure);
            throw new PaymentFailedException("Payment failed for orderId: " + savedOrder.getId());
        }
    }

    private void sendPaymentSuccessNotification(UserResponse user, Order order) {
        NotificationEvent success = new NotificationEvent(
                user.getId(),
                "Payment Successful",
                "Payment Success",
                "Your payment was successful for order #" + order.getId(),
                NotificationType.PAYMENT_SUCCESS,
                NotificationChannel.EMAIL);
        notificationProducer.sendNotification(success);
    }

    @Override
    public OrderResponse getOrderById(UUID id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
        return orderMapper.toDto(order);
    }

    @Override
    public List<OrderResponse> getAllOrdersOfUser(UUID userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponse cancelOrder(UUID id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
        order.setStatus(OrderStatus.CANCELLED);
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toDto(savedOrder);
    }

}
