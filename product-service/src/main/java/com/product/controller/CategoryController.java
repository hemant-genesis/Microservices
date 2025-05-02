package com.product.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.dto.requests.CategoryRequest;
import com.product.dto.responses.CategoryResponse;
import com.product.service.abstractions.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Tag(name = "Category controller", description = "APIs for managing categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "Create a new category")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Category created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        CategoryResponse categoryResponse = categoryService.createCategory(categoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryResponse);
    }

    @Operation(summary = "Get a category by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Category found"),
        @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable String id) {
        CategoryResponse categoryResponse = categoryService.getCategoryById(id);
        return ResponseEntity.ok(categoryResponse);
    }

    @Operation(summary = "Get all categories")
    @ApiResponse(responseCode = "201", description = "List of categories")
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<CategoryResponse> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @Operation(summary = "Update a category")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Category updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(
            @PathVariable String id,
            @RequestBody @Validated CategoryRequest categoryRequest) {
        CategoryResponse updatedCategory = categoryService.updateCategory(id, categoryRequest);
        return ResponseEntity.ok(updatedCategory);
    }

    @Operation(summary = "Delete a category by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Category deleted"),
        @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable String id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
