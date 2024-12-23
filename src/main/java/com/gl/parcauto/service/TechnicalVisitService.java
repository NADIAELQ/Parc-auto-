package com.gl.parcauto.service;


import com.gl.parcauto.dto.request.TechnicalVisitDtoRequest;
import com.gl.parcauto.dto.response.TechnicalVisitDtoResponse;

import java.util.List;

public interface TechnicalVisitService {
    TechnicalVisitDtoResponse create(String licensePlate, TechnicalVisitDtoRequest request);
    TechnicalVisitDtoResponse getById(String licensePlate, Long technicalVisitId);
    List<TechnicalVisitDtoResponse> getTechnicalVisitsForVehicle(String licensePlate);
    TechnicalVisitDtoResponse update(String licensePlate, Long technicalVisitId, TechnicalVisitDtoRequest request);
    void delete(String licensePlate, Long technicalVisitId);

}
