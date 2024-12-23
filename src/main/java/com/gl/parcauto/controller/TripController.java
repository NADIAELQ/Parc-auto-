package com.gl.parcauto.controller;

import com.gl.parcauto.dto.request.DriverVehicleToTripDto;
import com.gl.parcauto.dto.request.TripDtoRequest;
import com.gl.parcauto.dto.response.TripDtoResponse;
import com.gl.parcauto.service.TripService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/trips")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class TripController {
    private final TripService tripService;
    @PostMapping
    public ResponseEntity<TripDtoResponse> createTrip(@Valid @RequestBody TripDtoRequest tripDto) {
        TripDtoResponse response = tripService.create(tripDto);
        return ResponseEntity.created(URI.create("/trips/" + response.id()))
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TripDtoResponse> getTripById(@PathVariable(name = "id") Long id) {
        TripDtoResponse response = tripService.getById(id);
        return ResponseEntity.ok(response);
    }
    @GetMapping
    public ResponseEntity<List<TripDtoResponse>> getAllTrip() {
        List<TripDtoResponse> responseList = tripService.getAllTrip();
        return ResponseEntity.ok(responseList);
    }
    @GetMapping(params = {"startTrip", "endTrip"})
    public ResponseEntity<List<TripDtoResponse>> getAllTripBetweenDates(
            @RequestParam(name = "startTrip")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam(name = "endTrip")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        List<TripDtoResponse> responses = tripService.getAllTripBetweenDates(start, end);
        return ResponseEntity.ok(responses);
    }
    @PutMapping("/{id}/assign")
    public ResponseEntity<TripDtoResponse> assignDriverAndVehicleToTrip(
            @PathVariable(name = "id") Long id, @Valid @RequestBody DriverVehicleToTripDto dto) {
        TripDtoResponse response = tripService.assignDriverAndVehicleToTrip(id, dto);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/{id}")
    public ResponseEntity<TripDtoResponse> updateTrip(
            @PathVariable(name = "id") Long id, @Valid @RequestBody TripDtoRequest tripDto) {
        TripDtoResponse response = tripService.update(id, tripDto);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTrip(@PathVariable(name = "id") Long id) {
        tripService.delete(id);
        return ResponseEntity.ok("Trip deleted successfully");
    }
}
