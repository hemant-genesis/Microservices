package com.order.entity;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(nullable = false)
    private String productId; // ID from Product Service

    @Column(nullable = false)
    private String productName; // Snapshot of product name

    @Column(nullable = false)
    private BigDecimal price; // Snapshot of product price

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private BigDecimal totalItemAmount;

    @PrePersist
    protected void calculateTotal() {
        this.totalItemAmount = price.multiply(BigDecimal.valueOf(quantity));
    }
}

