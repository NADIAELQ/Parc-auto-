package com.gl.parcauto.service.impl;

import com.gl.parcauto.dto.request.TechnicalVisitDtoRequest;
import com.gl.parcauto.dto.response.TechnicalVisitDtoResponse;
import com.gl.parcauto.entity.TechnicalVisit;
import com.gl.parcauto.entity.Vehicle;
import com.gl.parcauto.exception.AppException;
import com.gl.parcauto.exception.ResourceNotFoundException;
import com.gl.parcauto.mapper.TechnicalVisitMapper;
import com.gl.parcauto.repository.TechnicalVisitRepository;
import com.gl.parcauto.repository.VehicleRepository;
import com.gl.parcauto.service.TechnicalVisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TechnicalVisitServiceImpl implements TechnicalVisitService {
    private final TechnicalVisitRepository technicalVisitRepository;
    private final VehicleRepository vehicleRepository;

    @Override
    public TechnicalVisitDtoResponse create(String licensePlate, TechnicalVisitDtoRequest request) {
        // Get vehicle from database
        Vehicle vehicle = vehicleRepository.findById(licensePlate).orElseThrow(
                () -> new ResourceNotFoundException("Vehicle", "id", licensePlate)
        );

        // DTO to entity
        TechnicalVisit technicalVisit = TechnicalVisitMapper.INSTANCE.visitDtoRequestToTechnicalVisit(request);

        // Add vehicle to technical visit
        technicalVisit.setVehicle_technical_visit(vehicle);

        // Save technical visit in database
        TechnicalVisit saved = technicalVisitRepository.save(technicalVisit);

        // Add technical visit to vehicle
        vehicle.getTechnicalVisits().add(saved);

        // Save vehicle update in database
        vehicleRepository.save(vehicle);

        return TechnicalVisitMapper.INSTANCE.visitToTechnicalVisitDtoResponse(saved);
    }

    @Override
    public List<TechnicalVisitDtoResponse> getTechnicalVisitsForVehicle(String licensePlate) {
        // Get vehicle from database
        Vehicle vehicle = vehicleRepository.findById(licensePlate).orElseThrow(
                () -> new ResourceNotFoundException("Vehicle", "id", licensePlate)
        );

        return vehicle.getTechnicalVisits().stream()
                .map(TechnicalVisitMapper.INSTANCE::visitToTechnicalVisitDtoResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TechnicalVisitDtoResponse getById(String licensePlate, Long technicalVisitId) {
        // Get vehicle from database
        Vehicle vehicle = vehicleRepository.findById(licensePlate).orElseThrow(
                () -> new ResourceNotFoundException("Vehicle", "id", licensePlate)
        );

        // Get technical visit from database
        TechnicalVisit technicalVisit = technicalVisitRepository.findById(technicalVisitId).orElseThrow(
                () -> new ResourceNotFoundException("Technical visit", "id", technicalVisitId.toString())
        );

        // Validate technical visit for vehicle
        validateTechnicalVisitForVehicle(vehicle, technicalVisit);

        return TechnicalVisitMapper.INSTANCE.visitToTechnicalVisitDtoResponse(technicalVisit);
    }

    @Override
    public TechnicalVisitDtoResponse update(String licensePlate, Long technicalVisitId, TechnicalVisitDtoRequest request) {
        // Get vehicle from database
        Vehicle vehicle = vehicleRepository.findById(licensePlate).orElseThrow(
                () -> new ResourceNotFoundException("Vehicle", "id", licensePlate)
        );

        // Get technical visit from database
        TechnicalVisit technicalVisit = technicalVisitRepository.findById(technicalVisitId).orElseThrow(
                () -> new ResourceNotFoundException("Technical visit", "id", technicalVisitId.toString())
        );

        // validate technical visit for vehicle
        validateTechnicalVisitForVehicle(vehicle, technicalVisit);

        if(request.visitDate() != null) {
            technicalVisit.setVisitDate(request.visitDate());
        }

        // Save technical visit update in database
        TechnicalVisit updated = technicalVisitRepository.save(technicalVisit);

        return TechnicalVisitMapper.INSTANCE.visitToTechnicalVisitDtoResponse(updated);
    }

    @Override
    public void delete(String licensePlate, Long technicalVisitId) {
        // Get vehicle from database
        Vehicle vehicle = vehicleRepository.findById(licensePlate).orElseThrow(
                () -> new ResourceNotFoundException("Vehicle", "id", licensePlate)
        );

        // Get technical visit from database
        TechnicalVisit technicalVisit = technicalVisitRepository.findById(technicalVisitId).orElseThrow(
                () -> new ResourceNotFoundException("Technical visit", "id", technicalVisitId.toString())
        );

        // validate technical visit for vehicle
        validateTechnicalVisitForVehicle(vehicle, technicalVisit);
    }
    private void validateTechnicalVisitForVehicle(Vehicle vehicle, TechnicalVisit technicalVisit) {
        if(!technicalVisit.getVehicle_technical_visit().getLicensePlate().equals(vehicle.getLicensePlate()))
            throw new AppException(HttpStatus.NOT_FOUND, String.format("Technical visit not found for vehicle with ID %s", vehicle.getLicensePlate()));
    }
}
