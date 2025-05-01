package com.product.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.product.entity.Inventory;

@Repository
public interface InventoryRepository extends MongoRepository<Inventory, String> {
    // Custom query methods can be added here
}
