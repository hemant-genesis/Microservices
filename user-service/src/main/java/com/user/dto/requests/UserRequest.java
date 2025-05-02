package com.user.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request object for registering a user")
public class UserRequest {
    
    @Schema(description = "User's first name", example = "John", required = true)
    @NotBlank(message = "First name is required")
    private String firstName;

    @Schema(description = "User's last name", example = "Doe", required = true)
    @NotBlank(message = "Last name is required")
    private String lastName;

    @Schema(description = "User's email address", example = "john@example.com", required = true)
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @Schema(description = "User's password", example = "pass12", required = true)
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @Schema(description = "User's phone", example = "9234567890", required = true)
    @NotBlank(message = "Phone is required")
    private String phone;
}
