package com.gl.parcauto.dto.response;

import com.gl.parcauto.entity.Role;
import java.io.Serializable;

/**
 * A DTO for the {@link Role} entity
 */
public record RoleDtoResponse(Long id, String name) implements Serializable {
}