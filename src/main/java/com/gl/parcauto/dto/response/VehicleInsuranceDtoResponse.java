package com.gl.parcauto.dto.response;

import com.gl.parcauto.entity.InsuranceDuration;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DTO for the {@link com.gl.parcauto.entity.VehicleInsurance} entity
 */
public record VehicleInsuranceDtoResponse(Long id, LocalDate activatedDate,
                                          InsuranceDuration duration) implements Serializable {
}