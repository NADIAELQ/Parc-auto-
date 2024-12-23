package com.gl.parcauto.dto.response;

import com.gl.parcauto.entity.DriverLicenseType;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link com.gl.parcauto.entity.DriverLicense} entity
 */
public record DriverLicenseDtoResponse(Long id,
                                       LocalDateTime deliveryDate,
                                       DriverLicenseType licenseType,
                                       String driverCin) implements Serializable {
}