package com.gl.parcauto.service;

import com.gl.parcauto.dto.request.VacationDtoRequest;
import com.gl.parcauto.dto.response.VacationDtoResponse;

import java.util.List;

public interface VacationService {
    VacationDtoResponse create(String cin, VacationDtoRequest vacationDto);
    VacationDtoResponse getById(String cin, Long vacationId);
    List<VacationDtoResponse> getVacationsForDriver(String cin);
    VacationDtoResponse update(String cin, Long vacationId, VacationDtoRequest vacationDto);
    void delete(String cin, Long vacationId);
}
