package com.product.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class CategoryRequest {
    
    @NotBlank(message = "Category name must not be blank")
    private String name;

    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;
}
