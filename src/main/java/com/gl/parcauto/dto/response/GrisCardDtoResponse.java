package com.gl.parcauto.dto.response;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DTO for the {@link com.gl.parcauto.entity.GrisCard} entity
 */
public record GrisCardDtoResponse(Long id, LocalDate activatedDate) implements Serializable {
}