package com.user.service.abstractions;

import java.util.List;

import com.user.dto.requests.UserRequest;
import com.user.dto.responses.UserResponse;

public interface UserService {
    UserResponse registerUser(UserRequest userRegistrationRequest);
    UserResponse getUserById(String id);
    List<UserResponse> getAllUsers();
    void deleteUserById(String id);
}
