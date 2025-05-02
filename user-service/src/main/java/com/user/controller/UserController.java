package com.user.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.dto.requests.UserRequest;
import com.user.dto.responses.UserResponse;
import com.user.service.abstractions.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@Tag(name = "User Controller", description = "APIs for managing users")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;

    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User successfully registered"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody UserRequest userRequestDTO) {
        return new ResponseEntity<>(userService.registerUser(userRequestDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Get a user by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User found"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable String id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }


    @Operation(summary = "Get all users")
    @ApiResponse(responseCode = "200", description = "List of users")
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Operation(summary = "Delete a user by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "User deleted"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable String id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
