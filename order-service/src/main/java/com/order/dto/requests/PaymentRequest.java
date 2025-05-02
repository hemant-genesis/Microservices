package com.order.dto.requests;

import java.math.BigDecimal;

import com.order.enums.PaymentMethod;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Payment details for the order")
public class PaymentRequest {

    @Schema(description = "Order ID associated with the payment", example = "order-123")
    @NotBlank(message = "Order id required")
    private String orderId;

    @Schema(description = "Payment method used", example = "CREDIT_CARD")
    @NotNull(message = "payment method is required")
    private PaymentMethod paymentMethod;

    @Schema(description = "Amount paid", example = "250.75")
    @NotNull(message = "Amount is required")
    @Min(value = 0, message = "Amount must be at least 0")
    private BigDecimal amount;
}
