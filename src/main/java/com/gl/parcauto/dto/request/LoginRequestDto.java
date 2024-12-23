package com.gl.parcauto.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginRequestDto (
        @NotNull(message = "Username is required")
        @NotBlank(message = "Username cannot be blank")
        String username,
        @NotNull(message = "Password is required")
        @NotBlank(message = "Password cannot be blank")
        String password){
}
