# ecommerce-microservices
Ecommerce project using microservices architecture.

## User Service entities
class User {
    UUID id;
    String email;
    String password;
    String firstName;
    String lastName;
    String phone;
    Set<Address> addresses;
    Set<Role> roles;
}

class Role {
    UUID id;
    String roleName;
    Set<User> users;
}

class Address {
    UUID id;
    String street;
    String city;
    String state;
    String country;
    String zipCode;
    User user;
}

class UserProfile {
    private UUID id;
    private String profilePicture;
    @OneToOne
    private User user;
}

class VerificationToken {
    UUID id;
    String token;
    User user;
    LocalDateTime expiryDate
}

## Product Service entities.

class Category {
    String id;
    String name;
    String description;
    List<Product> products; // Can be embedded or referenced by ID
}

class Product {
    String id;
    String name;
    String description;
    BigDecimal price;
    boolean available;
    String categoryId; // Embedded or referenced by ID
    List<String> imagesUrl;
    List<ProductReview> reviews; // Embedded documents
    List<Discount> discounts; // Embedded documents
    Inventory inventory; // Embedded or referenced by ID
}

class ProductReview {
    String id;
    String review;
    int rating;
}

class Inventory {
    String id;
    int quantity;
}

class Discount {
    String id;
    BigDecimal discountPercentage;
    LocalDateTime startDate;
    LocalDateTime endDate;
}

## Order Service

class Order {
    UUID id;
    UUID userId;
    BigDecimal totalAmount;
    OrderStatus status;
    @OneToMany
    List<OrderItem> orderItems;
    @OneToOne
    Payment payment;
    @OneToOne
    Shipping shipping;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}

class OrderItem {
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    Order order;
    UUID productId; // ID from Product Service
    String productName; // Snapshot of product name
    BigDecimal price; // Snapshot of product price
    int quantity;
    BigDecimal totalItemAmount;
}

class Shipping {
    UUID id;
    String recipientName;
    String address;
    String city;
    String state;
    String postalCode;
    String country;
    String shippingMethod; // e.g., STANDARD, EXPRESS
    String trackingNumber; // Provided by the logistics provider
    ShippingStatus status;
    LocalDateTime shippedAt;
    LocalDateTime deliveredAt;
}

class Payment {
    UUID id;
    BigDecimal amount;
    PaymentStatus paymentStatus; // e.g., PENDING, SUCCESS, FAILED
    String transactionId; // Reference from the Payment Service
    String paymentMethod; // e.g., CREDIT_CARD, PAYPAL
    @OneToOne(mappedBy = "payment", fetch = FetchType.LAZY)
    Order order;
    LocalDateTime createdAt;
}

## Notification Service

class Notification {
    String id;
    String userId;
    String message;
    NotificationType type;
    NotificationChannel channel;
    NotificationStatus status;
    LocalDateTime createdAt;
    LocalDateTime sentAt;
}

class NotificationEvent {
    String userId;
    String title;
    String subject;
    String body;
    NotificationType type;
    NotificationChannel channel;
}

class NotificationTemplate {
    String id;
    NotificationType type;
    String subject;
    String body;
}