package com.user.service.implementations;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.user.dto.requests.UserRequest;
import com.user.dto.responses.UserResponse;
import com.user.entity.User;
import com.user.exceptions.UserNotFoundException;
import com.user.repository.UserRepository;
import com.user.service.abstractions.UserService;
import com.user.utils.Utils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponse registerUser(UserRequest userRegistrationRequest) {
        User user = Utils.toUserEntity(userRegistrationRequest);
        User savedUser = userRepository.save(user);
        return Utils.toUserResponse(savedUser);
    }

    @Override
    public UserResponse getUserById(String id) {
        User user = userRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        return Utils.toUserResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> Utils.toUserResponse(user)).collect(Collectors.toList());
    }

    @Override
    public void deleteUserById(String id) {
        User user = userRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        
        userRepository.delete(user);
    }

}
