package com.product.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Document(collection = "products")
public class Product {

    @Id
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private boolean available;
    private String categoryId; // Embedded or referenced by ID
    private List<String> imagesUrl;
    private List<ProductReview> reviews; // Embedded documents
    private List<Discount> discounts; // Embedded documents
    private Inventory inventory; // Embedded or referenced by ID
}
