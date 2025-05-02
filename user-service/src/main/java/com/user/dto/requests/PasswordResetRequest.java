package com.user.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request object for password reset")
public class PasswordResetRequest {
    @Schema(description = "User's email address", example = "john@example.com", required = true)
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;
}
