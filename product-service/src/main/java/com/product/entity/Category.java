package com.product.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

import java.util.List;

@Data
@Document(collection = "categories")
public class Category {

    @Id
    private String id;
    private String name;
    private String description;
    private List<Product> products; // Can be embedded or referenced by ID
}

