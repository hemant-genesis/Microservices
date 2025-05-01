package com.product.service.implementations;

import java.util.List;

import org.springframework.stereotype.Service;

import com.product.dto.requests.CategoryRequest;
import com.product.dto.responses.CategoryResponse;
import com.product.entity.Category;
import com.product.exception.ResourceNotFoundException;
import com.product.repository.CategoryRepository;
import com.product.service.abstractions.CategoryService;
import com.product.utils.CategoryConverter;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    
    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        Category category = CategoryConverter.toCategory(categoryRequest);
        Category savedCategory = categoryRepository.save(category);
        return CategoryConverter.toCategoryResponse(savedCategory);
    }

    @Override
    public CategoryResponse getCategoryById(String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        return CategoryConverter.toCategoryResponse(category);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(CategoryConverter::toCategoryResponse)
                .toList();
    }

    @Override
    public CategoryResponse updateCategory(String id, CategoryRequest categoryRequest) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));

        existingCategory.setName(categoryRequest.getName());
        existingCategory.setDescription(categoryRequest.getDescription());
        
        Category updatedCategory = categoryRepository.save(existingCategory);
        return CategoryConverter.toCategoryResponse(updatedCategory);
    }

    @Override
    public boolean deleteCategory(String id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
        return true;
    }
}
