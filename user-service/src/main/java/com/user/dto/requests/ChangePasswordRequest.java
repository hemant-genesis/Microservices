package com.user.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request object for change password")
public class ChangePasswordRequest {
    @Schema(description = "User's password for change", example = "pass12", required = true)
    @Size(min = 6, message = "Password must be at least 6 characters")
    @NotBlank(message = "Password is required")
    private String newPassword;
}
