package com.notification.service.abstractions;

import com.notification.dto.UserDto;

public interface UserService {
    UserDto getUserById(String userId);
}
