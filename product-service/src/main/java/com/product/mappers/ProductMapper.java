package com.product.mappers;

import java.util.List;

import org.springframework.stereotype.Component;

import com.product.dto.requests.ProductRequest;
import com.product.dto.responses.ProductResponse;
import com.product.entity.Category;
import com.product.entity.Inventory;
import com.product.entity.Product;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    private final CategoryMapper categoryMapper;

    // Convert ProductRequest to Product
    public Product toProduct(ProductRequest productRequest, String categoryId, Inventory inventory) {
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setCategoryId(categoryId); // Assuming category is passed or fetched by ID
        product.setImagesUrl(productRequest.getImages());
        product.setReviews(List.of()); // If reviews are not part of request, initialize as empty
        product.setDiscounts(List.of()); // If discounts are not part of request, initialize as empty
        product.setInventory(inventory); // Assuming inventory is passed or fetched by ID
        return product;
    }

    // Convert Product to ProductRequest
    public ProductRequest toProductRequest(Product product) {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setName(product.getName());
        productRequest.setDescription(product.getDescription());
        productRequest.setPrice(product.getPrice());
        productRequest.setCategoryId(product.getCategoryId() != null ? product.getCategoryId() : null);
        productRequest.setImages(product.getImagesUrl());
        return productRequest;
    }

    // Convert Product to ProductResponse
    public ProductResponse toProductResponse(Product product, Category category) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setDescription(product.getDescription());
        productResponse.setPrice(product.getPrice());
        productResponse.setAvailable(product.isAvailable());

        // Assuming inventory quantity is stored as a field in Inventory object
        productResponse.setQuantity(product.getInventory() != null ? String.valueOf(product.getInventory().getQuantity()) : "0");

        productResponse.setImagesUrl(product.getImagesUrl());
        
        // Convert Category to CategoryResponse
        if (product.getCategoryId() != null) {
            productResponse.setCategory(categoryMapper.toCategoryResponse(category));
        }
        
        return productResponse;
    }
}

