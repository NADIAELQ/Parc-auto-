package com.gl.parcauto.dto.response;

import java.io.Serializable;

/**
 * A DTO for the {@link com.gl.parcauto.entity.Driver} entity
 */
public record DriverUserDtoResponse(String cin, String firstName, String lastName, Long userId, String username,
                                    String roleName) implements Serializable {
}