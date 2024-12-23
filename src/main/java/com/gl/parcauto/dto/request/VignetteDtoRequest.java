package com.gl.parcauto.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DTO for the {@link com.gl.parcauto.entity.Vignette} entity
 */
public record VignetteDtoRequest(
        @NotNull(message = "Delivery date is required")
        @FutureOrPresent(message = "Delivery date must be in the present or future")
        LocalDate deliveryDate
) implements Serializable {
}