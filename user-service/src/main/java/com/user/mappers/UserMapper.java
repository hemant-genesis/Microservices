package com.user.mappers;

import org.springframework.stereotype.Component;

import com.user.dto.requests.UserRequest;
import com.user.dto.responses.UserResponse;
import com.user.entity.User;

@Component
public class UserMapper {
    public User toEntity(UserRequest userRequest) {
        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setPhone(userRequest.getPhone());
        return user;
    }

    public UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId().toString(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone());
    }
}
