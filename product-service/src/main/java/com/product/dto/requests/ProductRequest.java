package com.product.dto.requests;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ProductRequest {
    private String name;
    private String description;
    private BigDecimal price;
    private String categoryId;
    private List<String> images;
}
