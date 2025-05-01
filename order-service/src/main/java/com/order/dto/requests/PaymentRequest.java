package com.order.dto.requests;

import java.math.BigDecimal;

import com.order.enums.PaymentMethod;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentRequest {

    @NotBlank(message = "Order id required")
    private String orderId;

    @NotBlank(message = "payment method id required")
    private PaymentMethod paymentMethod;

    @NotNull(message = "Amount is required")
    @Min(value = 0, message = "Amount must be at least 0")
    private BigDecimal amount;
}
