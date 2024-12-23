package com.gl.parcauto.controller;

import com.gl.parcauto.dto.request.DriverDtoRequest;
import com.gl.parcauto.dto.request.UserDtoRequest;
import com.gl.parcauto.dto.response.DriverDtoResponse;
import com.gl.parcauto.dto.response.DriverUserDtoResponse;
import com.gl.parcauto.dto.response.TripDtoResponse;
import com.gl.parcauto.service.DriverService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/drivers")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class DriverController {
    private final DriverService driverService;
    @PostMapping
    public ResponseEntity<DriverDtoResponse> createDriver(@Valid @RequestBody DriverDtoRequest driverDto) {
        DriverDtoResponse response = driverService.create(driverDto);
        return ResponseEntity.created(URI.create("/drivers/" + response.cin()))
                .body(response);
    }

    @GetMapping("/{cin}")
    public ResponseEntity<DriverDtoResponse> getDriverById(@PathVariable(name = "cin") String cin) {
        DriverDtoResponse response = driverService.getById(cin);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<DriverDtoResponse>> getAllDrivers() {
        List<DriverDtoResponse> responses = driverService.getAllDrivers();
        return ResponseEntity.ok(responses);
    }
    @GetMapping(params = {"startTrip", "endTrip"})
    public ResponseEntity<List<DriverDtoResponse>> getAvailableDriversBetweenDates(
            @RequestParam(name = "startTrip") LocalDateTime start,
            @RequestParam(name = "endTrip") LocalDateTime end) {
        List<DriverDtoResponse> responses = driverService.getAvailableDriversBetweenDates(start, end);
        return ResponseEntity.ok(responses);
    }
    @GetMapping("/{cin}/trips")
    public ResponseEntity<List<TripDtoResponse>> getTripsOfDriver(@PathVariable(name = "cin") String cin) {
        List<TripDtoResponse> responses = driverService.getTripsOfDriver(cin);
        return ResponseEntity.ok(responses);
    }
    @PutMapping("/{cin}")
    public ResponseEntity<DriverDtoResponse> updateDriver(
            @PathVariable(name = "cin") String cin,
            @Valid @RequestBody DriverDtoRequest driverDto) {
        DriverDtoResponse response = driverService.update(cin, driverDto);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/{cin}/user")
    public ResponseEntity<DriverUserDtoResponse> updateUserAccountForDriver(
            @PathVariable(name = "cin") String cin,
            @Valid @RequestBody UserDtoRequest userDtoRequest) {
        DriverUserDtoResponse response = driverService.updateUserAccountForDriver(cin, userDtoRequest);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{cin}/assign/user")
    public ResponseEntity<String> assignUserToDriver(
            @PathVariable(name = "cin") String cin,
            @RequestParam(name = "userId") Long userId) {
        driverService.assignUserToDriver(cin, userId);
        return ResponseEntity.ok("User assigned to driver successfully");
    }

    @DeleteMapping("/{cin}/user")
    public ResponseEntity<String> deleteDriverUserAccount(@PathVariable(name = "cin") String cin) {
        driverService.deleteDriverUserAccount(cin);
        return ResponseEntity.ok("User account of driver deleted successfully");
    }
    @DeleteMapping("/{cin}")
    public ResponseEntity<String> deleteDriver(@PathVariable(name = "cin") String driverId) {
        driverService.delete(driverId);
        return ResponseEntity.ok("Driver deleted successfully");
    }
}
