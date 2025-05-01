package com.product.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "product_reviews")
public class ProductReview {

    @Id
    private String id;
    private String review;
    private int rating;
}

