package com.order.mappers;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.order.dto.requests.OrderItemRequest;
import com.order.dto.requests.OrderRequest;
import com.order.dto.requests.ShippingRequest;
import com.order.dto.responses.OrderItemResponse;
import com.order.dto.responses.OrderResponse;
import com.order.dto.responses.ShippingResponse;
import com.order.entity.Order;
import com.order.entity.OrderItem;
import com.order.entity.Shipping;
import com.order.enums.OrderStatus;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final UUIDMapper uuidMapper;

    private OrderItem toEntity(OrderItemRequest request) {
        OrderItem item = new OrderItem();
        item.setProductId(request.getProductId());
        item.setQuantity(request.getQuantity());
        item.setPrice(request.getPrice());
        return item;
    }

    private OrderItemResponse toDto(OrderItem item) {
        OrderItemResponse response = new OrderItemResponse();
        response.setProductId(item.getProductId());
        response.setQuantity(item.getQuantity());
        response.setPrice(item.getPrice());
        return response;
    }

    public Order toEntity(OrderRequest request) {
        Order order = new Order();
        order.setUserId(uuidMapper.toUUID(request.getUserId()));
        order.setTotalAmount(request.getTotalAmount());
        order.setStatus(OrderStatus.valueOf(request.getStatus().toUpperCase()));
        order.setOrderItems(request.getOrderItems().stream()
                .map((item) -> toEntity(item))
                .collect(Collectors.toList()));
        // order.setPayment(toEntity(request.getPayment()));
        order.setShipping(toEntity(request.getShipping()));
        return order;
    }

    public OrderResponse toDto(Order order) {
        OrderResponse response = new OrderResponse();
        response.setId(uuidMapper.toString(order.getId()));
        response.setUserId(uuidMapper.toString(order.getUserId()));
        response.setTotalAmount(order.getTotalAmount());
        response.setStatus(order.getStatus().name());
        response.setOrderItems(order.getOrderItems().stream()
                .map((item) -> toDto(item))
                .collect(Collectors.toList()));
        // response.setPayment(toDto(order.getPayment()));
        response.setShipping(toDto(order.getShipping()));
        response.setCreatedAt(order.getCreatedAt());
        response.setUpdatedAt(order.getUpdatedAt());
        return response;
    }

    private Shipping toEntity(ShippingRequest request) {
        Shipping shipping = new Shipping();
        shipping.setAddress(request.getAddress());
        shipping.setCity(request.getCity());
        shipping.setPostalCode(request.getPostalCode());
        shipping.setCountry(request.getCountry());
        return shipping;
    }

    private ShippingResponse toDto(Shipping shipping) {
        ShippingResponse response = new ShippingResponse();
        response.setAddress(shipping.getAddress());
        response.setCity(shipping.getCity());
        response.setPostalCode(shipping.getPostalCode());
        response.setCountry(shipping.getCountry());
        return response;
    }

    // private Payment toEntity(PaymentRequest request) {
    // Payment payment = new Payment();
    // payment.setMethod(request.getPaymentMethod());
    // payment.setAmount(request.getAmount());
    // return payment;
    // }

    // private PaymentResponse toDto(Payment payment) {
    // PaymentResponse response = new PaymentResponse();
    // response.setMethod(payment.getMethod());
    // response.setAmount(payment.getAmount());
    // response.setStatus(payment.getStatus());
    // return response;
    // }

}
