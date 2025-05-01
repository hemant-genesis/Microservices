package com.notification.service.consumers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.notification.service.abstractions.NotificationService;

@Service
public class NotificationConsumer {
    private final NotificationService notificationService;
    private Logger log = LoggerFactory.getLogger(NotificationConsumer.class);

    public NotificationConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "${app.kafka.topic}", groupId = "${app.kafka.group}")
    public void consumeNotification(String message) {
        log.info("Received message: " + message);
        notificationService.processNotification(message);
    }
}
