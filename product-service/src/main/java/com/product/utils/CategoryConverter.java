package com.product.utils;

import java.util.List;

import com.product.dto.requests.CategoryRequest;
import com.product.dto.responses.CategoryResponse;
import com.product.entity.Category;

public class CategoryConverter {

    public static Category toCategory(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.getName());
        category.setDescription(categoryRequest.getDescription());
        category.setProducts(List.of());
        return category;
    }

    public static CategoryResponse toCategoryResponse(Category category) {
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setId(category.getId());
        categoryResponse.setName(category.getName());
        categoryResponse.setDescription(category.getDescription());
        return categoryResponse;
    }
}
