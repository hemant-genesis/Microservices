package com.order.dto.responses;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.order.enums.PaymentMethod;
import com.order.enums.PaymentStatus;

import lombok.Data;

@Data
public class PaymentResponse {
    private String transactionId;
    private String orderId;
    private BigDecimal amount;
    private PaymentStatus status;
    private PaymentMethod method;
    private LocalDateTime createdAt;
}
