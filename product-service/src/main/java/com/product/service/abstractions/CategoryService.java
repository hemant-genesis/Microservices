package com.product.service.abstractions;

import java.util.List;

import com.product.dto.requests.CategoryRequest;
import com.product.dto.responses.CategoryResponse;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest categoryRequest);
    CategoryResponse getCategoryById(String id);
    List<CategoryResponse> getAllCategories();
    CategoryResponse updateCategory(String id, CategoryRequest categoryRequest);
    boolean deleteCategory(String id);
}
