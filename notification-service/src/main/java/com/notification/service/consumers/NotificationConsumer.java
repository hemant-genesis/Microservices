package com.notification.service.consumers;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.notification.service.abstractions.NotificationService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NotificationConsumer {
    private final NotificationService notificationService;

    public NotificationConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "${app.kafka.topic}", groupId = "${app.kafka.group}")
    public void consumeNotification(String message) {
        log.info("Received message: " + message);
        notificationService.processNotification(message);
    }
}
