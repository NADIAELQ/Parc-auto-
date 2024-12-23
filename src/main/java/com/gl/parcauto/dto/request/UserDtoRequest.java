package com.gl.parcauto.dto.request;

import com.gl.parcauto.entity.User;
import com.gl.parcauto.validation.PasswordValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * A DTO for the {@link User} entity
 */
public record UserDtoRequest (
        @NotEmpty(message = "Username is required")
        @NotBlank(message = "Username should not be blank")
        @Size(min = 5, message = "Username should have at least {min} characters")
        String username,
        @NotEmpty(message = "Password is required")
        @NotBlank(message = "Password should not be blank")
        @PasswordValid
        String password,
        @NotNull(message = "Role is required")
        RoleDtoRequest role
){}