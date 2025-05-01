package com.notification.entity;

import com.notification.enums.NotificationChannel;
import com.notification.enums.NotificationType;

import lombok.Data;

@Data
public class NotificationEvent {
    private String userId;
    private String title;
    private String subject;
    private String body;
    private NotificationType type;
    private NotificationChannel channel;
}

/* 
 * {
  "userId": "user123",
  "title": "Order Confirmed",
  "subject": "Your Order is Confirmed",
  "body": "Your order #1234 has been confirmed successfully!",
  "type": "ORDER_CONFIRMATION",
  "channel": "EMAIL"
}
 */