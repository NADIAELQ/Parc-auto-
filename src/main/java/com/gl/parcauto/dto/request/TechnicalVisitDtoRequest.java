package com.gl.parcauto.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DTO for the {@link com.gl.parcauto.entity.TechnicalVisit} entity
 */
public record TechnicalVisitDtoRequest(
        @NotNull(message = "Visit date is required")
        @FutureOrPresent
        LocalDate visitDate
) implements Serializable { }