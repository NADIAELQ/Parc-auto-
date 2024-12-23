package com.gl.parcauto.dto.response;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DTO for the {@link com.gl.parcauto.entity.Vignette} entity
 */
public record VignetteDtoResponse(Long id, LocalDate deliveryDate) implements Serializable {
}