package com.gl.parcauto.dto.response;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DTO for the {@link com.gl.parcauto.entity.TechnicalVisit} entity
 */
public record TechnicalVisitDtoResponse(
        Long id,
        String vehicleLicensePlate,
        LocalDate visitDate) implements Serializable {
}