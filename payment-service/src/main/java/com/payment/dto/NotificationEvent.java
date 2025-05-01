package com.payment.dto;


import com.payment.enums.NotificationChannel;
import com.payment.enums.NotificationType;

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
