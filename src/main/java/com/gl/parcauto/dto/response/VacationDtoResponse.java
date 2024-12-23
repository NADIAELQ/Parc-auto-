package com.gl.parcauto.dto.response;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link com.gl.parcauto.entity.Vacation} entity
 */
public record VacationDtoResponse(Long id, LocalDateTime start, LocalDateTime end,
                                  String driverCin) implements Serializable {
}