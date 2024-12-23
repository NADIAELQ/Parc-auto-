package com.gl.parcauto.controller;

import com.gl.parcauto.dto.request.VacationDtoRequest;
import com.gl.parcauto.dto.response.VacationDtoResponse;
import com.gl.parcauto.service.VacationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/drivers/{cin}/vacations")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class VacationController {
    private final VacationService vacationService;
    @PostMapping
    public ResponseEntity<VacationDtoResponse> createVacation(
            @PathVariable(name = "cin") String cin, @Valid @RequestBody VacationDtoRequest vacationDto) {
        VacationDtoResponse response = vacationService.create(cin, vacationDto);
        return ResponseEntity.created(URI.create("/drivers/" + cin + "/vacations/" + response.id()))
                .body(response);
    }
    @GetMapping("/{vacationId}")
    public ResponseEntity<VacationDtoResponse> getVacationById(
            @PathVariable(name = "cin") String cin,
            @PathVariable(name = "vacationId") Long vacationId) {
        VacationDtoResponse response = vacationService.getById(cin, vacationId);
        return ResponseEntity.ok(response);
    }
    @GetMapping
    public ResponseEntity<List<VacationDtoResponse>> getVacationsForDriver(
            @PathVariable(name = "cin") String cin) {
        List<VacationDtoResponse> responses = vacationService.getVacationsForDriver(cin);
        return ResponseEntity.ok(responses);
    }
    @PutMapping("/{vacationId}")
    public ResponseEntity<VacationDtoResponse> updateVacation(
            @PathVariable(name = "cin") String cin,
            @PathVariable(name = "vacationId") Long vacationId,
            @Valid @RequestBody VacationDtoRequest vacationDto) {
        VacationDtoResponse response = vacationService.update(cin, vacationId, vacationDto);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{vacationId}")
    public ResponseEntity<String> deleteVacation(
            @PathVariable(name = "cin") String cin,
            @PathVariable(name = "vacationId") Long vacationId) {
        vacationService.delete(cin, vacationId);
        return ResponseEntity.ok("Vacation deleted successfully");
    }
}
