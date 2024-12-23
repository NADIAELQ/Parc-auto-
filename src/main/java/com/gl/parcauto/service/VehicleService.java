package com.gl.parcauto.service;

import com.gl.parcauto.dto.request.VehicleDtoRequest;
import com.gl.parcauto.dto.response.*;
import com.gl.parcauto.entity.Trip;
import com.gl.parcauto.entity.Vehicle;

import java.time.LocalDateTime;
import java.util.List;

public interface VehicleService {
    VehicleDtoResponse create(VehicleDtoRequest vehicleDto);
    VehicleAllFieldsDtoResponse getById(String licensePlate);
    List<VehicleDtoResponse> getAllVehicle();
    List<VehicleDtoResponse> getAvailableVehiclesBetweenDates(LocalDateTime start, LocalDateTime end);
    VehicleDtoResponse update(String licensePlate, VehicleDtoRequest vehicleDto);
    void delete(String licensePlate);
}
