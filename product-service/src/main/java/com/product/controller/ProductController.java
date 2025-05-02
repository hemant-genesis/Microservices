package com.product.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.dto.requests.ProductRequest;
import com.product.dto.responses.ProductResponse;
import com.product.service.abstractions.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Tag(name = "Product Controller", description = "APIs for managing products")
public class ProductController {
    
    private final ProductService productService;

    @Operation(summary = "Create a new product")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Product created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/create")
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest productRequest) {
        ProductResponse createdProduct = productService.createProduct(productRequest);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all products")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Products found"),
        @ApiResponse(responseCode = "404", description = "Products not found")
    })
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<ProductResponse> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "Get products by ids")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Products founds"),
        @ApiResponse(responseCode = "404", description = "Products not found")
    })
    @PostMapping("/ids")
    public ResponseEntity<List<ProductResponse>> getProductsByProductsIds(@RequestBody List<String> ids) {
        List<ProductResponse> products = productService.getProductsByProductsIds(ids);
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "Get all product by category ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Get all products by category ID"),
        @ApiResponse(responseCode = "404", description = "Products not found"),
    })
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductResponse>> getAllProductByCategoryId(@PathVariable String categoryId) {
        List<ProductResponse> products = productService.getAllProductByCategoryId(categoryId);
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "Get all product by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found product"),
        @ApiResponse(responseCode = "404", description = "Product not found"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable String id) {
        ProductResponse product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @Operation(summary = "Delete a product by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Product deleted"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable String id, @Validated @RequestBody ProductRequest productRequest) {
        ProductResponse updatedProduct = productService.updateProduct(id, productRequest);
        return ResponseEntity.ok(updatedProduct);
    }
}
