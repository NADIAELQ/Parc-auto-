package com.gl.parcauto.dto.response;

import com.gl.parcauto.dto.response.GrisCardDtoResponse;
import com.gl.parcauto.entity.DriverLicenseType;
import com.gl.parcauto.entity.Vehicle;

import java.io.Serializable;

/**
 * A DTO for the {@link Vehicle} entity
 */
public record VehicleGrisCardDtoResponse(String licensePlate, DriverLicenseType type,
                                         GrisCardDtoResponse grisCard) implements Serializable {
}