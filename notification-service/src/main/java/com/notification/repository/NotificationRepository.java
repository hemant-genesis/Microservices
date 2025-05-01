package com.notification.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.notification.entity.Notification;
import com.notification.enums.NotificationStatus;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {
    List<Notification> findByStatusIn(List<NotificationStatus> statuses);
}
