package com.gl.parcauto.service.impl;

import com.gl.parcauto.dto.request.VehicleDtoRequest;
import com.gl.parcauto.dto.response.*;
import com.gl.parcauto.entity.*;
import com.gl.parcauto.exception.AppException;
import com.gl.parcauto.exception.ResourceNotFoundException;
import com.gl.parcauto.mapper.VehicleMapper;
import com.gl.parcauto.repository.*;
import com.gl.parcauto.service.AvailabilityService;
import com.gl.parcauto.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {
    private final VehicleRepository vehicleRepository;
    private final AvailabilityService availabilityService;
    @Override
    public VehicleDtoResponse create(VehicleDtoRequest vehicleDto) {
        // Check if already exist in database
        if(vehicleRepository.existsByLicensePlate(vehicleDto.licensePlate()))
            throw new AppException(HttpStatus.BAD_REQUEST, "Vehicle already exist");

        // DTO to entity
        Vehicle vehicle = VehicleMapper.INSTANCE.vehicleDtoRequestToVehicle(vehicleDto);

        // Save in database
        Vehicle saved = vehicleRepository.save(vehicle);

        return VehicleMapper.INSTANCE.vehicleToVehicleDtoResponse(saved);
    }

    @Override
    public VehicleAllFieldsDtoResponse getById(String licensePlate) {
        // Get vehicle from database
        Vehicle vehicle = vehicleRepository.findById(licensePlate).orElseThrow(
                () -> new ResourceNotFoundException("Vehicle", "id", licensePlate)
        );

        return VehicleMapper.INSTANCE.toVehicleAllFieldsDtoResponse(vehicle);
    }

    @Override
    public List<VehicleDtoResponse> getAllVehicle() {
        return vehicleRepository.findAll().stream()
                .map(VehicleMapper.INSTANCE::vehicleToVehicleDtoResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<VehicleDtoResponse> getAvailableVehiclesBetweenDates(LocalDateTime start, LocalDateTime end) {
        return vehicleRepository.findVehiclesWithoutTripsBetweenDates(start, end).stream()
                .filter(vehicle -> availabilityService.isAvailableVehicle(vehicle.getLicensePlate(), start, end))
                .map(VehicleMapper.INSTANCE::vehicleToVehicleDtoResponse)
                .collect(Collectors.toList());
    }

    @Override
    public VehicleDtoResponse update(String licensePlate, VehicleDtoRequest vehicleDto) {
        // Get vehicle from database
        Vehicle vehicle = vehicleRepository.findById(licensePlate).orElseThrow(
                () -> new ResourceNotFoundException("Vehicle", "id", licensePlate)
        );

        if(StringUtils.hasText(vehicleDto.licensePlate()))
            vehicle.setLicensePlate(vehicleDto.licensePlate());

        if(vehicleDto.year() != null)
            vehicle.setYear(vehicleDto.year());

        if(vehicleDto.type() != null)
            vehicle.setType(vehicleDto.type());

        // Save update in database
        Vehicle updatedVehicle = vehicleRepository.save(vehicle);

        return VehicleMapper.INSTANCE.vehicleToVehicleDtoResponse(updatedVehicle);
    }

    @Override
    public void delete(String licensePlate) {
        // Get vehicle from database
        Vehicle vehicle = vehicleRepository.findById(licensePlate).orElseThrow(
                () -> new ResourceNotFoundException("Vehicle", "id", licensePlate)
        );

        vehicleRepository.delete(vehicle);
    }
}
