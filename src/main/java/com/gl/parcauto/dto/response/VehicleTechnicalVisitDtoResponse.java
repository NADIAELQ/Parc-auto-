package com.gl.parcauto.dto.response;

import com.gl.parcauto.dto.response.TechnicalVisitDtoResponse;
import com.gl.parcauto.entity.DriverLicenseType;
import com.gl.parcauto.entity.Vehicle;

import java.io.Serializable;
import java.util.Set;

/**
 * A DTO for the {@link Vehicle} entity
 */
public record VehicleTechnicalVisitDtoResponse(String licensePlate, DriverLicenseType type,
                                               Set<TechnicalVisitDtoResponse> technicalVisits) implements Serializable {
}