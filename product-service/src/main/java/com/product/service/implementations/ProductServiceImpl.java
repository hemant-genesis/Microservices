package com.product.service.implementations;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.product.dto.requests.ProductRequest;
import com.product.dto.responses.ProductResponse;
import com.product.entity.Category;
import com.product.entity.Product;
import com.product.exception.DatabaseException;
import com.product.exception.ResourceNotFoundException;
import com.product.mappers.ProductMapper;
import com.product.repository.CategoryRepository;
import com.product.repository.ProductRepsitory;
import com.product.service.abstractions.ProductService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepsitory productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        log.info("Creating product with name: {}", productRequest.getName());
        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> {
                    log.warn("Category not found with ID: {}", productRequest.getCategoryId());
                    return new ResourceNotFoundException("Category not found with ID " + productRequest.getCategoryId());
                });

        try {
            Product product = productMapper.toProduct(productRequest, category.getId(), null);
            Product savedProduct = productRepository.save(product);
            return productMapper.toProductResponse(savedProduct, category);
        } catch (DataAccessException ex) {
            log.error("Database error while creating product", ex);
            throw new DatabaseException("Failed to create product. Please try again later.");
        }
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        log.info("Fetching all products");
        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(product -> {
                    Category category = findCategoryOrThrow(product.getCategoryId());
                    return productMapper.toProductResponse(product, category);
                })
                .toList();
    }

    @Override
    public List<ProductResponse> getProductsByProductsIds(List<String> ids) {
        log.info("Fetching products by IDs: {}", ids);
        List<Product> products = productRepository.findByIdIn(ids);

        return products.stream()
                .map(product -> {
                    Category category = findCategoryOrThrow(product.getCategoryId());
                    return productMapper.toProductResponse(product, category);
                })
                .toList();
    }

    @Override
    public List<ProductResponse> getAllProductByCategoryId(String categoryId) {
        log.info("Fetching all products for category ID: {}", categoryId);
        List<Product> products = productRepository.findByCategoryId(categoryId);

        return products.stream()
                .map(product -> {
                    Category category = findCategoryOrThrow(product.getCategoryId());
                    return productMapper.toProductResponse(product, category);
                })
                .toList();
    }

    @Override
    public ProductResponse getProductById(String id) {
        log.info("Fetching product with ID: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Product not found with ID: {}", id);
                    return new ResourceNotFoundException("Product not found with ID " + id);
                });

        Category category = findCategoryOrThrow(product.getCategoryId());
        return productMapper.toProductResponse(product, category);
    }

    @Override
    public ProductResponse updateProduct(String id, ProductRequest productRequest) {
        log.info("Updating product with ID: {}", id);
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Product not found with ID: {}", id);
                    return new ResourceNotFoundException("Product not found with ID " + id);
                });

        Category category = findCategoryOrThrow(productRequest.getCategoryId());

        try {
            Product updatedProduct = productMapper.toProduct(productRequest, category.getId(), existingProduct.getInventory());
            updatedProduct.setId(existingProduct.getId());
            Product savedProduct = productRepository.save(updatedProduct);
            return productMapper.toProductResponse(savedProduct, category);
        } catch (DataAccessException ex) {
            log.error("Database error while updating product", ex);
            throw new DatabaseException("Failed to update product. Please try again later.");
        }
    }

    @Override
    public void deleteProduct(String id) {
        log.info("Deleting product with ID: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Product not found with ID: {}", id);
                    return new ResourceNotFoundException("Product not found with ID " + id);
                });

        try {
            productRepository.delete(product);
        } catch (DataAccessException ex) {
            log.error("Database error while deleting product", ex);
            throw new DatabaseException("Failed to delete product. Please try again later.");
        }
    }

    private Category findCategoryOrThrow(String categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> {
                    log.warn("Category not found with ID: {}", categoryId);
                    return new ResourceNotFoundException("Category not found with ID " + categoryId);
                });
    }
}
