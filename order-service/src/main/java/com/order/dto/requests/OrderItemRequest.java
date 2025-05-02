package com.order.dto.requests;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Details of a single item in the order")
public class OrderItemRequest {
    @Schema(description = "Product ID", example = "prod-abc123")
    private String productId;

    @Schema(description = "Quantity of the product", example = "2")
    private Integer quantity;

    @Schema(description = "Price of the product", example = "125.50")
    private BigDecimal price;
}
