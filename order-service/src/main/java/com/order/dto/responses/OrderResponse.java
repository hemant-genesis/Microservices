package com.order.dto.responses;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class OrderResponse {
    private String id;
    private String userId;
    private BigDecimal totalAmount;
    private String status;
    private List<OrderItemResponse> orderItems;
    private PaymentResponse payment;
    private ShippingResponse shipping;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
