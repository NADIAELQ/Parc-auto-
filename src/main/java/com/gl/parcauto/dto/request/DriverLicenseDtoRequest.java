package com.gl.parcauto.dto.request;

import com.gl.parcauto.entity.DriverLicenseType;
import com.gl.parcauto.validation.DeliveryDateValid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link com.gl.parcauto.entity.DriverLicense} entity
 */
public record DriverLicenseDtoRequest(
        @NotNull(message = "Delivery date is required")
        @Past(message = "Delivery date must be in the past")
        @DeliveryDateValid(message = "Delivery date must not be more than 10 years ago")
        LocalDateTime deliveryDate,
        @NotNull(message = "License type is required")
        DriverLicenseType licenseType
) implements Serializable { }