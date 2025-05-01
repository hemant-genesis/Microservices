package com.notification.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.notification.enums.NotificationChannel;
import com.notification.enums.NotificationStatus;
import com.notification.enums.NotificationType;

import lombok.Data;

@Data
@Document
public class Notification {
    @Id
    private String id;
    private String userId;
    private String message;
    private NotificationType type;
    private NotificationChannel channel;
    private NotificationStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime sentAt;
}
