package com.product.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.product.entity.Product;

public interface ProductRepsitory extends MongoRepository<Product, String> {
    List<Product> findByCategoryId(String categoryId);
    List<Product> findByIdIn(List<String> ids);
}
