package com.gl.parcauto.dto.request;

import com.gl.parcauto.validation.ActivatedDateValid;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DTO for the {@link com.gl.parcauto.entity.GrisCard} entity
 */
public record GrisCardDtoRequest(
        @NotNull(message = "Activated date is required")
        @ActivatedDateValid(message = "Activated date must not be more than 10 years ago")
        LocalDate activatedDate
) implements Serializable { }