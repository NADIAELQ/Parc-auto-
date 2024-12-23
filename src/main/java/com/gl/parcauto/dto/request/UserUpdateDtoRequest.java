package com.gl.parcauto.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * A DTO for the {@link com.gl.parcauto.entity.User} entity
 */
public record UserUpdateDtoRequest(
        @NotEmpty(message = "Username is required")
        @NotBlank(message = "Username should not be blank")
        @Size(min = 5, message = "Username should have at least {min} characters")
        String username
) implements Serializable {
}