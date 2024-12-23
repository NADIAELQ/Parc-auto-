package com.gl.parcauto.service.impl;

import com.gl.parcauto.dto.request.VignetteDtoRequest;
import com.gl.parcauto.dto.response.VignetteDtoResponse;
import com.gl.parcauto.entity.Vehicle;
import com.gl.parcauto.entity.Vignette;
import com.gl.parcauto.exception.AppException;
import com.gl.parcauto.exception.ResourceNotFoundException;
import com.gl.parcauto.mapper.VignetteMapper;
import com.gl.parcauto.repository.VehicleRepository;
import com.gl.parcauto.repository.VignetteRepository;
import com.gl.parcauto.service.VignetteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VignetteServiceImpl implements VignetteService {
    private final VignetteRepository vignetteRepository;
    private final VehicleRepository vehicleRepository;

    @Override
    public VignetteDtoResponse create(String licensePlate, VignetteDtoRequest request) {
        // Get vehicle from database
        Vehicle vehicle = vehicleRepository.findById(licensePlate).orElseThrow(
                () -> new ResourceNotFoundException("Vehicle", "id", licensePlate)
        );

        // DTO to entity
        Vignette vignette = VignetteMapper.INSTANCE.vignetteDtoRequestToVignette(request);

        // Add vehicle to vignette
        vignette.setVehicle(vehicle);

        // Save vignette in database
        Vignette saved = vignetteRepository.save(vignette);

        // Add vignette to vehicle
        vehicle.setVignette(saved);

        // Save vehicle update in database
        vehicleRepository.save(vehicle);

        return VignetteMapper.INSTANCE.vignetteToVignetteDtoResponse(vignette);
    }

    @Override
    public VignetteDtoResponse getById(String licensePlate, Long vignetteId) {
        // Get vehicle from database
        Vehicle vehicle = vehicleRepository.findById(licensePlate).orElseThrow(
                () -> new ResourceNotFoundException("Vehicle", "id", licensePlate)
        );

        // Get vignette from database
        Vignette vignette = vignetteRepository.findById(vignetteId).orElseThrow(
                () -> new ResourceNotFoundException("Vignette", "id", vignetteId.toString())
        );

        // Validate vignette for vehicle
        validateVignetteForVehicle(vehicle, vignette);

        return VignetteMapper.INSTANCE.vignetteToVignetteDtoResponse(vignette);
    }


    @Override
    public List<VignetteDtoResponse> getVignettesForVehicle(String licensePlate) {
        // Get vehicle from database
        Vehicle vehicle = vehicleRepository.findById(licensePlate).orElseThrow(
                () -> new ResourceNotFoundException("Vehicle", "id", licensePlate)
        );

        // Get vignette from vehicle
        Vignette vignette = vehicle.getVignette();

        if(vignette == null)
            return new ArrayList<>();

        return List.of(VignetteMapper.INSTANCE.vignetteToVignetteDtoResponse(vignette));
    }

    @Override
    public VignetteDtoResponse update(String licensePlate, Long vignetteId, VignetteDtoRequest request) {
        // Get vehicle from database
        Vehicle vehicle = vehicleRepository.findById(licensePlate).orElseThrow(
                () -> new ResourceNotFoundException("Vehicle", "id", licensePlate)
        );

        // Get vignette from database
        Vignette vignette = vignetteRepository.findById(vignetteId).orElseThrow(
                () -> new ResourceNotFoundException("Vignette", "id", vignetteId.toString())
        );

        // Validate vignette for vehicle
        validateVignetteForVehicle(vehicle, vignette);

        if(request.deliveryDate() != null)
            vignette.setDeliveryDate(request.deliveryDate());

        // Save vignette update in database
        Vignette updatedVignette = vignetteRepository.save(vignette);

        return VignetteMapper.INSTANCE.vignetteToVignetteDtoResponse(updatedVignette);
    }

    @Override
    public void delete(String licensePlate, Long vignetteId) {
        // Get vehicle from database
        Vehicle vehicle = vehicleRepository.findById(licensePlate).orElseThrow(
                () -> new ResourceNotFoundException("Vehicle", "id", licensePlate)
        );

        // Get vignette from database
        Vignette vignette = vignetteRepository.findById(vignetteId).orElseThrow(
                () -> new ResourceNotFoundException("Vignette", "id", vignetteId.toString())
        );

        // Validate vignette for vehicle
        validateVignetteForVehicle(vehicle, vignette);

        // Delete vignette from vehicle
        vehicle.setVignette(null);

        // Save update vehicle in database
        vehicleRepository.save(vehicle);

        // Delete vignette from database
        vignetteRepository.delete(vignette);
    }
    private void validateVignetteForVehicle(Vehicle vehicle, Vignette vignette) {
        if(!vignette.getVehicle().getLicensePlate().equals(vehicle.getLicensePlate()))
            throw new AppException(HttpStatus.NOT_FOUND, String.format("Vignette not found for vehicle with ID %s", vehicle.getLicensePlate()));
    }
}
