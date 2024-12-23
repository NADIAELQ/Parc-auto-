package com.gl.parcauto.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gl.parcauto.entity.DriverLicenseType;
import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.time.Year;
import java.util.Set;

/**
 * A DTO for the {@link com.gl.parcauto.entity.Vehicle} entity
 */
public record VehicleDtoRequest(
        @NotNull(message = "License plate is required")
        @NotBlank(message = "License plate cannot be blank")
        String licensePlate,
        @NotNull(message = "Year is required")
        @Min(value = 2010, message = "Year must be greater than or equal to {value}")
        Integer year,
        @NotNull(message = "Driver license type is required")
        DriverLicenseType type
) implements Serializable {
    private static final int CURRENT_YEAR = Year.now().getValue();
    @AssertTrue(message = "Year must be less than or equal to the current year")
    @JsonIgnore
    private boolean isValidYear() {
        return year <= CURRENT_YEAR;
    }
}