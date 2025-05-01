package com.order.entity;

// @Data
// @Entity
// @Table(name = "payments")
// public class Payment {
//     @Id
//     @GeneratedValue
//     private UUID id;

//     @Column
//     private String transactionId; // Reference from the Payment Service

//     @Column(nullable = false)
//     private BigDecimal amount;

//     @Enumerated(EnumType.STRING)
//     @Column(nullable = false)
//     private PaymentStatus status;

//     @Enumerated(EnumType.STRING)
//     @Column(nullable = false)
//     private PaymentMethod method;

//     @Column(nullable = false, updatable = false)
//     private LocalDateTime createdAt;

//     @Column(nullable = false)
//     private LocalDateTime updatedAt;

//     @OneToOne(mappedBy = "payment", fetch = FetchType.LAZY)
//     private Order order;
// }

