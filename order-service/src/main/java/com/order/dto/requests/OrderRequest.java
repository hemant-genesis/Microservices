package com.order.dto.requests;

import java.math.BigDecimal;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Request object for placing an order")
public class OrderRequest {
    
    @Schema(description = "ID of the user placing the order", example = "123e4567-e89b-12d3-a456-426614174000")
    @NotBlank(message = "User ID is required")
    private String userId;

    @Schema(description = "Total amount of the order", example = "250.75")
    @NotNull(message = "Total amount is required")
    @Min(value = 0, message = "Total amount must be at least 0")
    private BigDecimal totalAmount;

    @Schema(description = "Order status", example = "PLACED")
    @NotBlank(message = "Status is required")
    private String status;

    @Schema(description = "List of order items")
    @NotNull(message = "Order items cannot be null")
    private List<OrderItemRequest> orderItems;

    @Schema(description = "Payment information")
    @NotNull(message = "Payment details are required")
    private PaymentRequest payment;

    @Schema(description = "Shipping information")
    @NotNull(message = "Shipping details are required")
    private ShippingRequest shipping;
}
