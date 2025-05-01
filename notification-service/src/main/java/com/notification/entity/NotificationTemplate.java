package com.notification.entity;

import com.notification.enums.NotificationType;

import lombok.Data;

@Data
public class NotificationTemplate {
    private String id;
    private NotificationType type;
    private String subject;
    private String body;
}
