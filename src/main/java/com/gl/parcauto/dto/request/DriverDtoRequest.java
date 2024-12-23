package com.gl.parcauto.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DTO for the {@link com.gl.parcauto.entity.Driver} entity
 */
public record DriverDtoRequest(
        @NotNull(message = "Cin is required")
        @NotBlank(message = "Cin should not be blank")
        @Size(min = 5, message = "Cin should at least contain {min} characters")
        String cin,
        @NotNull(message = "First name is required")
        @NotBlank(message = "First name should not be blank")
        @Size(min = 3, message = "First name should at least contain {min} characters")
        String firstName,
        @NotNull(message = "Last name is required")
        @NotBlank(message = "Last name should not be blank")
        @Size(min = 3, message = "Last name should at least contain {min} characters")
        String lastName,
        @NotNull(message = "Date of birth is required")
        @Past(message = "Date of birth must be in the past")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate dateOfBirth
) implements Serializable { }