package com.gl.parcauto.dto.response;

import com.gl.parcauto.entity.DriverLicenseType;

import java.io.Serializable;

/**
 * A DTO for the {@link com.gl.parcauto.entity.Vehicle} entity
 */
public record VehicleVignetteDtoResponse(String licensePlate, DriverLicenseType type,
                                         VignetteDtoResponse vignette) implements Serializable {
}