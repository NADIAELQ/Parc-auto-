package com.gl.parcauto.service;

import com.gl.parcauto.entity.Trip;

public interface AffectationService {
    Trip DriverToVehicle(String driverId, String vehicleId, Long tripId);
}
