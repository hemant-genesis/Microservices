package com.product.service.implementations;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.product.dto.requests.CategoryRequest;
import com.product.dto.responses.CategoryResponse;
import com.product.entity.Category;
import com.product.exception.DatabaseException;
import com.product.exception.ResourceNotFoundException;
import com.product.mappers.CategoryMapper;
import com.product.repository.CategoryRepository;
import com.product.service.abstractions.CategoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    
    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        try {
            log.info("Creating new category: {}", categoryRequest.getName());
            Category category = categoryMapper.toCategory(categoryRequest);
            Category savedCategory = categoryRepository.save(category);
            return categoryMapper.toCategoryResponse(savedCategory);
        } catch (DataAccessException ex) {
            log.error("Database error while creating category", ex);
            throw new DatabaseException("Failed to create category. Please try again later.");
        }
    }

    @Override
    public CategoryResponse getCategoryById(String id) {
        log.info("Fetching category with id: {}", id);

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Category not found with id: {}", id);
                    return new ResourceNotFoundException("Category not found with id: " + id);
                });
        return categoryMapper.toCategoryResponse(category);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        log.info("Fetching all categories");

        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(categoryMapper::toCategoryResponse)
                .toList();
    }

    @Override
    public CategoryResponse updateCategory(String id, CategoryRequest categoryRequest) {
        log.info("Updating category with id: {}", id);

        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));

        existingCategory.setName(categoryRequest.getName());
        existingCategory.setDescription(categoryRequest.getDescription());

        try {
            Category updatedCategory = categoryRepository.save(existingCategory);
            return categoryMapper.toCategoryResponse(updatedCategory);
        } catch (DataAccessException ex) {
            log.error("Database error while updating category", ex);
            throw new DatabaseException("Failed to update category. Please try again later.");
        }
    }

    @Override
    public boolean deleteCategory(String id) {
        log.info("Deleting category with id: {}", id);
        if (!categoryRepository.existsById(id)) {
            log.warn("Attempted to delete non-existing category with id: {}", id);
            throw new ResourceNotFoundException("Category not found with id: " + id);
        }
        try {
            categoryRepository.deleteById(id);
            return true;
        } catch (DataAccessException ex) {
            log.error("Database error while deleting category", ex);
            throw new DatabaseException("Failed to delete category. Please try again later.");
        }
    }
}
