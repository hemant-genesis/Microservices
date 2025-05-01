package com.order.enums;

public enum ShippingStatus {
    PENDING,         // Shipment is pending and not yet processed
    PROCESSING,      // Shipment is being prepared
    SHIPPED,         // Shipment has been dispatched
    IN_TRANSIT,      // Shipment is on its way
    OUT_FOR_DELIVERY,// Shipment is out for delivery
    DELIVERED,       // Shipment has been delivered to the recipient
    RETURNED,        // Shipment has been returned to the sender
    CANCELLED        // Shipment has been cancelled
}
