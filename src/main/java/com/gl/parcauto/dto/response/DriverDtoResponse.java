package com.gl.parcauto.dto.response;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

/**
 * A DTO for the {@link com.gl.parcauto.entity.Driver} entity
 */
public record DriverDtoResponse(String cin, String firstName, String lastName, LocalDate dateOfBirth) implements Serializable {
}