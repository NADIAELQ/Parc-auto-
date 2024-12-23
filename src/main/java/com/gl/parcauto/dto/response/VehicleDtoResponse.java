package com.gl.parcauto.dto.response;

import com.gl.parcauto.entity.DriverLicenseType;

import java.io.Serializable;
import java.util.Set;

/**
 * A DTO for the {@link com.gl.parcauto.entity.Vehicle} entity
 */
public record VehicleDtoResponse(String licensePlate, Integer year, DriverLicenseType type) implements Serializable {
}