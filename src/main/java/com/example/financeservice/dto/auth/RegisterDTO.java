package com.example.financeservice.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Registration Request")
public class RegisterDTO {

    @Schema(description = "Username", example = "jon123")
    @Size(min = 2, max = 50, message = "Username must be between 5 and 50 characters")
    @NotBlank(message = "Username cannot be blank")
    private String username;

    @Schema(description = "Password", example = "my_1secret1_password")
    @Size(max = 255, message = "Password length should not exceed 255 characters")
    private String password;

    @Schema(description = "Email address", example = "example@example.com")
    private String email;

    @Schema(description = "First name", example = "John")
    private String firstName;

    @Schema(description = "Last name", example = "Doe")
    private String lastName;
}
