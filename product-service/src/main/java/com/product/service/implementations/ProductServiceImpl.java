package com.product.service.implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.product.dto.requests.ProductRequest;
import com.product.dto.responses.ProductResponse;
import com.product.entity.Category;
import com.product.entity.Product;
import com.product.exception.ResourceNotFoundException;
import com.product.repository.CategoryRepository;
import com.product.repository.ProductRepsitory;
import com.product.service.abstractions.ProductService;
import com.product.utils.ProductConverter;

import lombok.AllArgsConstructor;
@Service
@Validated
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepsitory productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID " + productRequest.getCategoryId()));

        Product product = ProductConverter.toProduct(productRequest, category.getId(), null);
        Product savedProduct = productRepository.save(product);
        return ProductConverter.toProductResponse(savedProduct, category);
    }
    
    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(product -> {
                    Category category = categoryRepository.findById(product.getCategoryId())
                            .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID " + product.getCategoryId()));
                    return ProductConverter.toProductResponse(product, category);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> getProductsByProductsIds(List<String> ids){
        List<Product> products = productRepository.findByIdIn(ids);
        return products.stream()
                .map(product -> {
                Category category = categoryRepository.findById(product.getCategoryId())
                        .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID " + product.getCategoryId()));
                return ProductConverter.toProductResponse(product, category);
                })
                .collect(Collectors.toList());
    }

    public List<ProductResponse> getAllProductByCategoryId(String categoryId) {
        return productRepository.findByCategoryId(categoryId).stream()
                .map(product -> {
                    Category category = categoryRepository.findById(product.getCategoryId())
                            .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID " + product.getCategoryId()));
                    return ProductConverter.toProductResponse(product, category);
                })
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse getProductById(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID " + id));
        
        Category category = categoryRepository.findById(product.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID " + product.getCategoryId()));

        return ProductConverter.toProductResponse(product, category);
    }

    @Override
    public ProductResponse updateProduct(String id, ProductRequest productRequest) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID " + id));
        
        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID " + productRequest.getCategoryId()));

        Product updatedProduct = ProductConverter.toProduct(productRequest, category.getId(), existingProduct.getInventory());
        updatedProduct.setId(existingProduct.getId());
        
        Product savedProduct = productRepository.save(updatedProduct);
        return ProductConverter.toProductResponse(savedProduct, category);
    }

    @Override
    public void deleteProduct(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID " + id));
        productRepository.delete(product);
    }
}
