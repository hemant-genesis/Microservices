package com.user.service.implementations;

import java.util.List;
import java.util.UUID;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.user.dto.requests.UserRequest;
import com.user.dto.responses.UserResponse;
import com.user.entity.User;
import com.user.exceptions.DatabaseException;
import com.user.exceptions.InvalidRequestException;
import com.user.exceptions.UserNotFoundException;
import com.user.mappers.UUIDMapper;
import com.user.mappers.UserMapper;
import com.user.repository.UserRepository;
import com.user.service.abstractions.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UUIDMapper uuidMapper;

    @Override
    public UserResponse registerUser(UserRequest userRegistrationRequest) {
        try {
            User user = userMapper.toEntity(userRegistrationRequest);
            User savedUser = userRepository.save(user);
            log.info("User registered with ID: {}", savedUser.getId());
            return userMapper.toResponse(savedUser);
        } catch (DataAccessException ex) {
            log.error("Database error while registering user ", ex);
            throw new DatabaseException("Failed to register user. Please try again later.");
        }
    }

    @Override
    public UserResponse getUserById(String id) {
        UUID userId = parseUUID(id);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("User not found with id: " + id);
                    return new UserNotFoundException("User not found with id: " + id);
                });
        return userMapper.toResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        try {
            return userRepository.findAll().stream().map(userMapper::toResponse).toList();
        } catch (DataAccessException ex) {
            log.error("Database error while fetching user ", ex);
            throw new DatabaseException("Failed to fetching user. Please try again later.");
        }
    }

    @Override
    public void deleteUserById(String id) {
        UUID userId = parseUUID(id);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("User not found with id: " + id);
                    return new UserNotFoundException("User not found with id: " + id);
                });

        try {
            userRepository.delete(user);
            log.info("User deleted with ID: {}", id);
        } catch (DataAccessException ex) {
            log.error("Database error while deleting user ", ex);
            throw new DatabaseException("Failed to deleting user. Please try again later.");
        }
    }

    private UUID parseUUID(String id) {
        try {
            return uuidMapper.toUUID(id);
        } catch (IllegalArgumentException e) {
            throw new InvalidRequestException("Invalid UUID format: " + id);
        }
    }

}
