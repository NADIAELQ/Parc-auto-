package com.gl.parcauto.dto.response;

import com.gl.parcauto.entity.DriverLicenseType;

import java.io.Serializable;
import java.util.Set;

/**
 * A DTO for the {@link com.gl.parcauto.entity.Vehicle} entity
 */
public record VehicleAllFieldsDtoResponse(String licensePlate, Integer year, DriverLicenseType type,
                                          GrisCardDtoResponse grisCard, VehicleInsuranceDtoResponse vehicleInsurance,
                                          VignetteDtoResponse vignette,
                                          Set<TechnicalVisitDtoResponse> technicalVisits) implements Serializable {
}