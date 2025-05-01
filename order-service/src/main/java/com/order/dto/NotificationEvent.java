package com.order.dto;

import com.order.enums.NotificationChannel;
import com.order.enums.NotificationType;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationEvent {
    private String userId;
    private String title;
    private String subject;
    private String body;
    private NotificationType type;
    private NotificationChannel channel;
}
