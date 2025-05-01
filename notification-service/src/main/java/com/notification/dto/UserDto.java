package com.notification.dto;

import java.util.Map;

import com.notification.enums.NotificationType;

import lombok.Data;

@Data
public class UserDto {
    private String id;
    private String email;
    private String phoneNumber;
    private Map<NotificationType, Boolean> preferences;
}
