package com.gl.parcauto.service.impl;

import com.gl.parcauto.dto.request.VehicleInsuranceDtoRequest;
import com.gl.parcauto.dto.response.VehicleInsuranceDtoResponse;
import com.gl.parcauto.entity.Vehicle;
import com.gl.parcauto.entity.VehicleInsurance;
import com.gl.parcauto.exception.AppException;
import com.gl.parcauto.exception.ResourceNotFoundException;
import com.gl.parcauto.mapper.VehicleInsuranceMapper;
import com.gl.parcauto.repository.VehicleInsuranceRepository;
import com.gl.parcauto.repository.VehicleRepository;
import com.gl.parcauto.service.VehicleInsuranceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleInsuranceServiceImpl implements VehicleInsuranceService {
    private final VehicleInsuranceRepository insuranceRepository;
    private final VehicleRepository vehicleRepository;
    @Override
    public VehicleInsuranceDtoResponse create(String licensePlate, VehicleInsuranceDtoRequest request) {
        // Get vehicle from database
        Vehicle vehicle = vehicleRepository.findById(licensePlate).orElseThrow(
                () -> new ResourceNotFoundException("Vehicle", "id", licensePlate)
        );

        // DTO to entity
        VehicleInsurance vehicleInsurance = VehicleInsuranceMapper.INSTANCE.toEntity(request);

        // Add vehicle to vehicle insurance
        vehicleInsurance.setVehicle(vehicle);

        // Save vehicle insurance in database
        VehicleInsurance saved = insuranceRepository.save(vehicleInsurance);

        // Add insurance to vehicle
        vehicle.setVehicleInsurance(saved);

        // Save vehicle update in database
        vehicleRepository.save(vehicle);

        return VehicleInsuranceMapper.INSTANCE.toDto(saved);
    }

    @Override
    public VehicleInsuranceDtoResponse getById(String licensePlate, Long insuranceId) {
        // Get vehicle from database
        Vehicle vehicle = vehicleRepository.findById(licensePlate).orElseThrow(
                () -> new ResourceNotFoundException("Vehicle", "id", licensePlate)
        );

        // Get insurance from database
        VehicleInsurance insurance = insuranceRepository.findById(insuranceId).orElseThrow(
                () -> new ResourceNotFoundException("Vehicle insurance", "id", insuranceId.toString())
        );

        // validate insurance for vehicle
        validateInsuranceForVehicle(vehicle, insurance);

        return VehicleInsuranceMapper.INSTANCE.toDto(insurance);
    }

    @Override
    public List<VehicleInsuranceDtoResponse> getInsurancesForVehicle(String licensePlate) {
        // Get vehicle from database
        Vehicle vehicle = vehicleRepository.findById(licensePlate).orElseThrow(
                () -> new ResourceNotFoundException("Vehicle", "id", licensePlate)
        );

        // Get insurance from vehicle
        VehicleInsurance insurance = vehicle.getVehicleInsurance();

        if(insurance == null)
            return new ArrayList<>();

        return List.of(VehicleInsuranceMapper.INSTANCE.toDto(insurance));
    }

    @Override
    public VehicleInsuranceDtoResponse update(String licensePlate, Long insuranceId, VehicleInsuranceDtoRequest request) {
        // Get vehicle from database
        Vehicle vehicle = vehicleRepository.findById(licensePlate).orElseThrow(
                () -> new ResourceNotFoundException("Vehicle", "id", licensePlate)
        );

        // Get insurance from database
        VehicleInsurance insurance = insuranceRepository.findById(insuranceId).orElseThrow(
                () -> new ResourceNotFoundException("Vehicle insurance", "id", insuranceId.toString())
        );

        // validate insurance for vehicle
        validateInsuranceForVehicle(vehicle, insurance);

        if(request.activatedDate() != null) {
            insurance.setActivatedDate(request.activatedDate());
        }

        if(request.duration() != null) {
            insurance.setDuration(request.duration());
        }

        // Save insurance update in database
        VehicleInsurance updated = insuranceRepository.save(insurance);

        return VehicleInsuranceMapper.INSTANCE.toDto(updated);
    }

    @Override
    public void delete(String licensePlate, Long insuranceId) {
        // Get vehicle from database
        Vehicle vehicle = vehicleRepository.findById(licensePlate).orElseThrow(
                () -> new ResourceNotFoundException("Vehicle", "id", licensePlate)
        );

        // Get insurance from database
        VehicleInsurance insurance = insuranceRepository.findById(insuranceId).orElseThrow(
                () -> new ResourceNotFoundException("Vehicle insurance", "id", insuranceId.toString())
        );

        // validate insurance for vehicle
        validateInsuranceForVehicle(vehicle, insurance);

        // Delete insurance from database
        insuranceRepository.delete(insurance);
    }
    private void validateInsuranceForVehicle(Vehicle vehicle, VehicleInsurance insurance) {
        if(!insurance.getVehicle().getLicensePlate().equals(vehicle.getLicensePlate()))
            throw new AppException(HttpStatus.NOT_FOUND, String.format("Insurance not found for vehicle with ID %s", vehicle.getLicensePlate()));
    }
}
