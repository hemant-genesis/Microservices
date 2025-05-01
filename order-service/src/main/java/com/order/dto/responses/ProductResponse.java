package com.order.dto.responses;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class ProductResponse {
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private boolean available;
    private String quantity;
    private List<String> imagesUrl;
    private CategoryResponse category;
}
