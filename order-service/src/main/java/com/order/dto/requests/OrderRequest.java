package com.order.dto.requests;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderRequest {
    
    @NotBlank(message = "User ID is required")
    private String userId;

    @NotNull(message = "Total amount is required")
    @Min(value = 0, message = "Total amount must be at least 0")
    private BigDecimal totalAmount;

    @NotBlank(message = "Status is required")
    private String status; // Use a string to allow flexibility in parsing enums

    @NotNull(message = "Order items cannot be null")
    private List<OrderItemRequest> orderItems;

    @NotNull(message = "Payment details are required")
    private PaymentRequest payment;

    @NotNull(message = "Shipping details are required")
    private ShippingRequest shipping;
}
