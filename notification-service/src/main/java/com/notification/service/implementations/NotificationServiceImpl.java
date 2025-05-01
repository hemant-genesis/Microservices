package com.notification.service.implementations;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.notification.dto.UserDto;
import com.notification.entity.Notification;
import com.notification.entity.NotificationEvent;
import com.notification.entity.NotificationTemplate;
import com.notification.enums.NotificationStatus;
import com.notification.repository.NotificationRepository;
import com.notification.service.abstractions.NotificationService;
import com.notification.service.abstractions.UserService;


@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserService userService;

    private final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);
    
    public NotificationServiceImpl(
        NotificationRepository notificationRepository,
        UserService userService
    ){
        this.notificationRepository = notificationRepository;
        this.userService = userService;
    };

    @Override
    public void processNotification(String message) {
        NotificationEvent event = parseMessage(message);
        processEvent(event);
    }

    @Scheduled(fixedDelay = 60000)
    public void processRetries() {
        // Fetch failed notifications and retry sending them
        List<Notification> failedNotifications = notificationRepository.findByStatusIn(List.of(NotificationStatus.FAILED, NotificationStatus.RETRYING));
        for (Notification notification : failedNotifications) {
            retrySending(notification);
        }
    }

    private NotificationEvent parseMessage(String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(message, NotificationEvent.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse message", e);
        }
    }

    private void processEvent(NotificationEvent event) {
        UserDto user = userService.getUserById(event.getUserId());
        String finalMessage = event.getBody(); // for now

        Notification notification = new Notification();
        notification.setUserId(event.getUserId());
        notification.setMessage(finalMessage);
        notification.setType(event.getType());
        notification.setChannel(event.getChannel());
        notification.setStatus(NotificationStatus.PENDING);
        notification.setCreatedAt(LocalDateTime.now());

        try {
            sendNotification(user, event.getChannel().toString(), finalMessage);
            notification.setStatus(NotificationStatus.SENT);
            notification.setSentAt(LocalDateTime.now());
        } catch (Exception e) {
            log.error("Failed to send notification", e);
            notification.setStatus(NotificationStatus.FAILED);
        }

        notificationRepository.save(notification);

        // UserDto user = getUser(event.getUserId());
        // NotificationTemplate template = getTemplate(event.getType(), user.getPreferredChannel());
        // String message = generateMessage(template, event.getPayload());
        // sendNotification(user, template.getChannel(), message);
        // logNotification(event.getUserId(), event.getType(), template.getChannel());
    }

    private void sendNotification(UserDto user, String channel, String message) {
        switch (channel) {
            case "EMAIL":
                sendEmail(user,message);
                break;
            case "SMS":
                sendSms(user,message);
                break;
        }
    }

    private void sendEmail(UserDto user, String message) {
        log.info("Sending Email to {}: {}", user.getEmail(), message);
    }

    private void sendSms(UserDto user, String message) {
        log.info("Sending SMS to {}: {}", user.getPhoneNumber(), message);
    }

    private void retrySending(Notification notification) {
        try {
            UserDto user = userService.getUserById(notification.getUserId());
            sendNotification(user, notification.getChannel().toString(), notification.getMessage());
            notification.setStatus(NotificationStatus.SENT);
            notification.setSentAt(LocalDateTime.now());
        } catch (Exception e) {
            log.error("Retry failed for notification {}", notification.getId(), e);
            notification.setStatus(NotificationStatus.RETRYING);
        }
        notificationRepository.save(notification);
    }
}
