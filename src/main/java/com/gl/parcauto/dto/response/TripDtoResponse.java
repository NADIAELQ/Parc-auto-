package com.gl.parcauto.dto.response;

import com.gl.parcauto.entity.DriverLicenseType;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link com.gl.parcauto.entity.Trip} entity
 */
public record TripDtoResponse(Long id, String driverCin, String vehicleLicensePlate, LocalDateTime startDate,
                              LocalDateTime endDate, String startTrip, String endTrip,
                              DriverLicenseType type) implements Serializable {
}