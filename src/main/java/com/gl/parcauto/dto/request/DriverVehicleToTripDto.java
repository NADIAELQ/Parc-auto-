package com.gl.parcauto.dto.request;

import jakarta.validation.constraints.NotBlank;

public record DriverVehicleToTripDto(
        @NotBlank(message = "Driver ID is mandatory")
        String driverId,
        @NotBlank(message = "Vehicle ID is mandatory")
        String vehicleId
) {
}
