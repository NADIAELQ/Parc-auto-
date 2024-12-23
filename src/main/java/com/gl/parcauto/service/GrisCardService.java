package com.gl.parcauto.service;


import com.gl.parcauto.dto.request.GrisCardDtoRequest;
import com.gl.parcauto.dto.response.GrisCardDtoResponse;

import java.util.List;

public interface GrisCardService {
    GrisCardDtoResponse create(String licensePlate, GrisCardDtoRequest cardDtoRequest);
    GrisCardDtoResponse getById(String licensePlate, Long grisCardId);
    List<GrisCardDtoResponse> getGrisCardsForVehicle(String licensePlate);
    GrisCardDtoResponse update(String licensePlate, Long grisCardId, GrisCardDtoRequest cardDtoRequest);
    void delete(String licensePlate, Long grisCardId);

}
