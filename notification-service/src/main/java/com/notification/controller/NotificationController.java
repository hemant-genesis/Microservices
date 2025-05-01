package com.notification.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    @GetMapping
    public String sendNotification(@RequestBody String messageEntity) {
        
        return "";
    }
    
}
