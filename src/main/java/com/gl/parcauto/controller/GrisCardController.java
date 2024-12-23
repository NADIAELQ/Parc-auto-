package com.gl.parcauto.controller;

import com.gl.parcauto.dto.request.GrisCardDtoRequest;
import com.gl.parcauto.dto.response.GrisCardDtoResponse;
import com.gl.parcauto.service.GrisCardService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicles/{licensePlate}/grisCards")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class GrisCardController {
    private final GrisCardService grisCardService;
    @PostMapping
    public ResponseEntity<GrisCardDtoResponse> createGrisCard(
            @PathVariable(name = "licensePlate") String licensePlate,
            @Valid @RequestBody GrisCardDtoRequest cardDtoRequest) {
        GrisCardDtoResponse response = grisCardService.create(licensePlate, cardDtoRequest);
        return ResponseEntity.created(URI.create("/vehicles/" + licensePlate + "/grisCards/" + response.id()))
                .body(response);
    }
    @GetMapping("/{grisCardId}")
    public ResponseEntity<GrisCardDtoResponse> getGrisCardById(
            @PathVariable(name = "licensePlate") String licensePlate,
            @PathVariable(name = "grisCardId") Long grisCardId) {
        GrisCardDtoResponse response = grisCardService.getById(licensePlate, grisCardId);
        return ResponseEntity.ok(response);
    }
    @GetMapping
    public ResponseEntity<List<GrisCardDtoResponse>> getGrisCardsForVehicle(
            @PathVariable(name = "licensePlate") String licensePlate) {
        List<GrisCardDtoResponse> responses = grisCardService.getGrisCardsForVehicle(licensePlate);
        return ResponseEntity.ok(responses);
    }
    @PutMapping("/{grisCardId}")
    public ResponseEntity<GrisCardDtoResponse> updateGrisCard(
            @PathVariable(name = "licensePlate") String licensePlate,
            @PathVariable(name = "grisCardId") Long grisCardId,
            @Valid @RequestBody GrisCardDtoRequest cardDtoRequest) {
        GrisCardDtoResponse response = grisCardService.update(licensePlate, grisCardId, cardDtoRequest);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{grisCardId}")
    public ResponseEntity<String> deleteGrisCard(
            @PathVariable(name = "licensePlate") String licensePlate,
            @PathVariable(name = "grisCardId") Long grisCardId) {
        grisCardService.delete(licensePlate, grisCardId);
        return ResponseEntity.ok("Gris card deleted successfully");
    }
}
