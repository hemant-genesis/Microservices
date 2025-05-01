package com.notification.service.implementations;

import org.springframework.stereotype.Service;

import com.notification.dto.UserDto;
import com.notification.service.abstractions.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public UserDto getUserById(String userId) {
        UserDto user = new UserDto();
        user.setId(userId);
        user.setEmail(userId + "@example.com");
        user.setPhoneNumber("1234567890");
        return user;
    }
}
