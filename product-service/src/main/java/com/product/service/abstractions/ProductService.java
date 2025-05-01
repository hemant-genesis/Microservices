package com.product.service.abstractions;

import java.util.List;

import com.product.dto.requests.ProductRequest;
import com.product.dto.responses.ProductResponse;

public interface ProductService {
    ProductResponse createProduct(ProductRequest productRequest);
    List<ProductResponse> getAllProducts();
    List<ProductResponse> getAllProductByCategoryId(String categoryId);
    List<ProductResponse> getProductsByProductsIds(List<String> ids);
    ProductResponse getProductById(String id);
    ProductResponse updateProduct(String id, ProductRequest productRequest);
    void deleteProduct(String id);
}
