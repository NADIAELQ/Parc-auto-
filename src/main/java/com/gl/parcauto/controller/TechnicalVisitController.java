package com.gl.parcauto.controller;

import com.gl.parcauto.dto.request.TechnicalVisitDtoRequest;
import com.gl.parcauto.dto.response.TechnicalVisitDtoResponse;
import com.gl.parcauto.service.TechnicalVisitService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicles/{licensePlate}/technicalVisits")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class TechnicalVisitController {
    private final TechnicalVisitService technicalVisitService;
    @PostMapping
    public ResponseEntity<TechnicalVisitDtoResponse> createTechnicalVisit(
            @PathVariable(name = "licensePlate") String licensePlate,
            @Valid @RequestBody TechnicalVisitDtoRequest request) {
        TechnicalVisitDtoResponse response = technicalVisitService.create(licensePlate, request);
        return ResponseEntity.created(URI.create("/vehicles/" + licensePlate + "/technicalVisits/" + response.id()))
                .body(response);
    }
    @GetMapping("/{technicalVisitId}")
    public ResponseEntity<TechnicalVisitDtoResponse> getTechnicalVisitById(
            @PathVariable(name = "licensePlate") String licensePlate,
            @PathVariable(name = "technicalVisitId") Long technicalVisitId) {
        TechnicalVisitDtoResponse response = technicalVisitService.getById(licensePlate, technicalVisitId);
        return ResponseEntity.ok(response);
    }
    @GetMapping
    public ResponseEntity<List<TechnicalVisitDtoResponse>> getTechnicalVisitsForVehicle(
            @PathVariable(name = "licensePlate") String licensePlate) {
        List<TechnicalVisitDtoResponse> responses = technicalVisitService.getTechnicalVisitsForVehicle(licensePlate);
        return ResponseEntity.ok(responses);
    }
    @PutMapping("/{technicalVisitId}")
    public ResponseEntity<TechnicalVisitDtoResponse> updateTechnicalVisit(
            @PathVariable(name = "licensePlate") String licensePlate,
            @PathVariable(name = "technicalVisitId") Long technicalVisitId,
            @Valid @RequestBody TechnicalVisitDtoRequest request) {
        TechnicalVisitDtoResponse response = technicalVisitService.update(licensePlate, technicalVisitId, request);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{technicalVisitId}")
    public ResponseEntity<String> deleteTechnicalVisit(
            @PathVariable(name = "licensePlate") String licensePlate,
            @PathVariable(name = "technicalVisitId") Long technicalVisitId) {
        technicalVisitService.delete(licensePlate, technicalVisitId);
        return ResponseEntity.ok("Technical visit deleted successfully");
    }
}
