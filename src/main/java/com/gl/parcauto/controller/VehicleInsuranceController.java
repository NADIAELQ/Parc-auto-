package com.gl.parcauto.controller;

import com.gl.parcauto.dto.request.VehicleInsuranceDtoRequest;
import com.gl.parcauto.dto.response.VehicleInsuranceDtoResponse;
import com.gl.parcauto.service.VehicleInsuranceService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicles/{licensePlate}/insurances")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class VehicleInsuranceController {
    private final VehicleInsuranceService insuranceService;
    @PostMapping
    public ResponseEntity<VehicleInsuranceDtoResponse> createInsurance(
            @PathVariable(name = "licensePlate") String licensePlate,
            @Valid @RequestBody VehicleInsuranceDtoRequest request) {
        VehicleInsuranceDtoResponse response = insuranceService.create(licensePlate, request);
        return ResponseEntity.created(URI.create("/vehicles/" + licensePlate + "/insurances/" + response.id()))
                .body(response);
    }
    @GetMapping("/{insuranceId}")
    public ResponseEntity<VehicleInsuranceDtoResponse> getInsuranceById(
            @PathVariable(name = "licensePlate") String licensePlate,
            @PathVariable(name = "insuranceId") Long insuranceId) {
        VehicleInsuranceDtoResponse response = insuranceService.getById(licensePlate, insuranceId);
        return ResponseEntity.ok(response);
    }
    @GetMapping
    public ResponseEntity<List<VehicleInsuranceDtoResponse>> getInsurancesForVehicle(
            @PathVariable(name = "licensePlate") String licensePlate) {
        List<VehicleInsuranceDtoResponse> response = insuranceService.getInsurancesForVehicle(licensePlate);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/{insuranceId}")
    public ResponseEntity<VehicleInsuranceDtoResponse> updateInsurance(
            @PathVariable(name = "licensePlate") String licensePlate,
            @PathVariable(name = "insuranceId") Long insuranceId,
            @Valid @RequestBody VehicleInsuranceDtoRequest request) {
        VehicleInsuranceDtoResponse response = insuranceService.update(licensePlate, insuranceId, request);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{insuranceId}")
    public ResponseEntity<String> deleteInsurance(
            @PathVariable(name = "licensePlate") String licensePlate,
            @PathVariable(name = "insuranceId") Long insuranceId) {
        insuranceService.delete(licensePlate, insuranceId);
        return ResponseEntity.ok("Vehicle insurance deleted successfully");
    }
}
