package com.product.dto.requests;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ProductRequest {

    @NotBlank(message = "Product name must not be blank")
    private String name;

    @NotNull(message = "Description must not be null")
    private String description;

    @NotNull(message = "Price must not be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;

    @NotBlank(message = "Category ID is required")
    private String categoryId;
    
    private List<String> images;
}
