package com.product.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.product.entity.Category;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {
    // Custom query methods can be added here
}