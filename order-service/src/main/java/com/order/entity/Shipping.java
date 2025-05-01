package com.order.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.order.enums.ShippingStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "shipping")
public class Shipping {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String recipientName;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String postalCode;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String shippingMethod; // e.g., STANDARD, EXPRESS

    @Column
    private String trackingNumber; // Provided by the logistics provider

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShippingStatus status;

    @Column
    private LocalDateTime shippedAt;

    @Column
    private LocalDateTime deliveredAt;
}

