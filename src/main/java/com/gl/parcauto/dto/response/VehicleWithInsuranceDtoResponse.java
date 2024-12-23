package com.gl.parcauto.dto.response;

import com.gl.parcauto.entity.DriverLicenseType;
import com.gl.parcauto.entity.Vehicle;

import java.io.Serializable;

/**
 * A DTO for the {@link Vehicle} entity
 */
public record VehicleWithInsuranceDtoResponse(String licensePlate, DriverLicenseType type,
                                              VehicleInsuranceDtoResponse vehicleInsurance) implements Serializable {
}