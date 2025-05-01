package com.order.dto.responses;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class OrderItemResponse {
    private String productId;
    private Integer quantity;
    private BigDecimal price;
}
