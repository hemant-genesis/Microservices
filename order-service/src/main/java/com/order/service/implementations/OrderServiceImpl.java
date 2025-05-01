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
import com.order.exception.ResourceNotFoundException;
import com.order.repository.OrderRepository;
import com.order.service.abstractions.OrderService;
import com.order.service.producers.NotificationProducer;
import com.order.utils.OrderConverter;
import com.order.utils.Utils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserClient userServiceClient;
    private final ProductClient productClient;
    private final NotificationProducer notificationProducer;
    private final PaymentClient paymentClient;

    @Override
    public OrderResponse placeOrder(OrderRequest request) {
        // 1. Fetch user
        UserResponse user = userServiceClient.getUserById(request.getUserId().toString());
        if (user == null) {
            throw new RuntimeException("User not found with ID: " + request.getUserId());
        }

        // 2. Fetch product details
        List<String> productIds = request.getOrderItems()
            .stream()
            .map(OrderItemRequest::getProductId)
            .toList();
        List<ProductResponse> products = productClient.getProductsByIdList(productIds);

        if (products == null || products.isEmpty()) {
            throw new RuntimeException("No products found for the given IDs.");
        }

         // 3. Map productId to ProductResponse for easy lookup
        Map<String, ProductResponse> productMap = products.stream()
            .collect(Collectors.toMap(ProductResponse::getId, p -> p));

        // 4. Build Order Items (enriching price and product name from ProductResponse)
        List<OrderItem> orderItems = request.getOrderItems()
        .stream()
        .map(orderItemRequest -> {
            ProductResponse product = productMap.get(orderItemRequest.getProductId());

            if (product == null) {
                throw new RuntimeException("Product not found with ID: " + orderItemRequest.getProductId());
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(product.getId());
            orderItem.setProductName(product.getName());
            orderItem.setPrice(product.getPrice());
            orderItem.setQuantity(orderItemRequest.getQuantity());
            return orderItem;
        })
        .toList();

         // 5. Calculate total price
        BigDecimal totalPrice = orderItems.stream()
            .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 6. Create Order entity
        Order order = new Order();
        order.setUserId(Utils.toUUID(user.getId()));
        order.setTotalAmount(totalPrice);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setCreatedAt(LocalDateTime.now());
        orderItems.forEach(item -> item.setOrder(order));
        order.setOrderItems(orderItems);
        Shipping shipping = new Shipping();
        shipping.setAddress(request.getShipping().getAddress());
        shipping.setCity(request.getShipping().getCity());
        shipping.setCountry(request.getShipping().getCountry());
        shipping.setPostalCode(request.getShipping().getPostalCode());
        order.setShipping(shipping);

        // 7. Save order
        Order savedOrder = orderRepository.save(order);

        // 8. Send notification to user
        NotificationEvent orderNotificationEvent = new NotificationEvent(
            user.getId(),
            "Order Confirmed",
            "Order Confirmation",
            "Your order #" + savedOrder.getId() + " has been confirmed successfully!",
            NotificationType.ORDER_CONFIRMATION,
            NotificationChannel.EMAIL
        );
        notificationProducer.sendNotification(orderNotificationEvent);

        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setOrderId(Utils.toString(savedOrder.getId()));
        paymentRequest.setAmount(totalPrice);
        paymentRequest.setPaymentMethod(request.getPayment().getPaymentMethod());

        PaymentResponse paymentResponse = paymentClient.processPayment(paymentRequest);

        if (paymentResponse == null || paymentResponse.getStatus() != PaymentStatus.SUCCESS) {
            NotificationEvent notificationEvent = new NotificationEvent(
                user.getId(),
                "Payment failed",
                "Payment failed",
                "Your payment is failed",
                NotificationType.PAYMENT_FAILED,
                NotificationChannel.EMAIL
            );
            notificationProducer.sendNotification(notificationEvent);
            throw new RuntimeException("Payment failed!");
        }

        NotificationEvent paymentNotificationEvent = new NotificationEvent(
            user.getId(),
            "Payment done",
            "Payment sucessfull",
            "Your payment is sucessfull with transaction id = " + paymentResponse.getTransactionId(),
            NotificationType.PAYMENT_SUCCESS,
            NotificationChannel.EMAIL
        );
        notificationProducer.sendNotification(paymentNotificationEvent);

        // 9. Return order response
        return OrderConverter.toDto(savedOrder);
    }

    @Override
    public OrderResponse getOrderById(UUID id) {
        Order order = orderRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
        return OrderConverter.toDto(order);
    }

    @Override
    public List<OrderResponse> getAllOrdersOfUser(UUID userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream()
                .map(OrderConverter::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponse updateOrder(UUID id, OrderRequest request) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
        Order updatedOrder = OrderConverter.toEntity(request);
        updatedOrder.setId(existingOrder.getId()); // Preserve the ID
        Order savedOrder = orderRepository.save(updatedOrder);
        return OrderConverter.toDto(savedOrder);
    }

    @Override
    public OrderResponse cancelOrder(UUID id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
        order.setStatus(OrderStatus.CANCELLED);
        Order savedOrder = orderRepository.save(order);
        return OrderConverter.toDto(savedOrder);
    }

}
