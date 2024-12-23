package com.gl.parcauto.dto.request;

import com.gl.parcauto.validation.PasswordValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;


public record UserPasswordDtoRequest(
        @NotEmpty(message = "Password is required")
        @NotBlank(message = "Password should not be blank")
        @PasswordValid
        String newPassword,
        @NotEmpty(message = "Password is required")
        @NotBlank(message = "Password should not be blank")
        @PasswordValid
        String confirmedNewPassword
) {
}
