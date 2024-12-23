package com.gl.parcauto.dto.response;

import com.gl.parcauto.entity.Driver;
import com.gl.parcauto.entity.DriverLicenseType;
import com.gl.parcauto.entity.Trip;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * A DTO for the {@link Driver} entity
 */
public record DriverTripDtoResponse(Set<TripDto> trips) implements Serializable {
    /**
     * A DTO for the {@link Trip} entity
     */
    public record TripDto(String vehicleLicensePlate, LocalDateTime startDate, LocalDateTime endDate,
                          String startTrip, String endTrip, DriverLicenseType type) implements Serializable {
    }
}