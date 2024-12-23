package com.gl.parcauto.controller;

import com.gl.parcauto.dto.request.VignetteDtoRequest;
import com.gl.parcauto.dto.response.VignetteDtoResponse;
import com.gl.parcauto.service.VignetteService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicles/{licensePlate}/vignettes")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class VignetteController {
    private final VignetteService vignetteService;
    @PostMapping
    public ResponseEntity<VignetteDtoResponse> createVignette(
            @PathVariable(name = "licensePlate") String licensePlate,
            @Valid @RequestBody VignetteDtoRequest request) {
        VignetteDtoResponse response = vignetteService.create(licensePlate, request);
        return ResponseEntity.created(URI.create("/vehicles/" + licensePlate + "/vignettes/" + response.id()))
                .body(response);
    }
    @GetMapping("/{vignetteId}")
    public ResponseEntity<VignetteDtoResponse> getVignetteById(
            @PathVariable(name = "licensePlate") String licensePlate,
            @PathVariable(name = "vignetteId") Long vignetteId) {
        VignetteDtoResponse response = vignetteService.getById(licensePlate, vignetteId);
        return ResponseEntity.ok(response);
    }
    @GetMapping
    public ResponseEntity<List<VignetteDtoResponse>> getVignettesForVehicle(
            @PathVariable(name = "licensePlate") String licensePlate) {
        List<VignetteDtoResponse> responses = vignetteService.getVignettesForVehicle(licensePlate);
        return ResponseEntity.ok(responses);
    }
    @PutMapping("/{vignetteId}")
    public ResponseEntity<VignetteDtoResponse> updateVignette(
            @PathVariable(name = "licensePlate") String licensePlate,
            @PathVariable(name = "vignetteId") Long vignetteId,
            @Valid @RequestBody VignetteDtoRequest request) {
        VignetteDtoResponse response = vignetteService.update(licensePlate, vignetteId, request);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{vignetteId}")
    public ResponseEntity<String> deleteVignette(
            @PathVariable(name = "licensePlate") String licensePlate,
            @PathVariable(name = "vignetteId") Long vignetteId) {
        vignetteService.delete(licensePlate, vignetteId);
        return ResponseEntity.ok("Vignette deleted successfully");
    }
}
