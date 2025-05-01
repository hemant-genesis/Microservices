package com.payment.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.payment.enums.PaymentMethod;
import com.payment.enums.PaymentStatus;

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
