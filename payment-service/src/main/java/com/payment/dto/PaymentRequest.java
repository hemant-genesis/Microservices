package com.payment.dto;

import java.math.BigDecimal;

import com.payment.enums.PaymentMethod;

import lombok.Data;

@Data
public class PaymentRequest {
    private String orderId;
    private BigDecimal amount;
    private PaymentMethod paymentMethod;
}
