package com.gl.parcauto.service;


import com.gl.parcauto.dto.request.VehicleInsuranceDtoRequest;
import com.gl.parcauto.dto.response.VehicleInsuranceDtoResponse;

import java.util.List;

public interface VehicleInsuranceService {
    VehicleInsuranceDtoResponse create(String licensePlate, VehicleInsuranceDtoRequest request);
    VehicleInsuranceDtoResponse getById(String licensePlate, Long insuranceId);
    List<VehicleInsuranceDtoResponse> getInsurancesForVehicle(String licensePlate);
    VehicleInsuranceDtoResponse update(String licensePlate, Long insuranceId, VehicleInsuranceDtoRequest request);
    void delete(String licensePlate, Long insuranceId);
}
