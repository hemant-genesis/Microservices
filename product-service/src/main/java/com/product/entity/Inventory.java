package com.product.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "inventories")
public class Inventory {

    @Id
    private String id;
    private int quantity;
}
