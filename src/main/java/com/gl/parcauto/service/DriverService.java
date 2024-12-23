package com.gl.parcauto.service;

import com.gl.parcauto.dto.request.DriverDtoRequest;
import com.gl.parcauto.dto.request.UserDtoRequest;
import com.gl.parcauto.dto.response.DriverDtoResponse;
import com.gl.parcauto.dto.response.DriverUserDtoResponse;
import com.gl.parcauto.dto.response.TripDtoResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface DriverService {
    DriverDtoResponse create(DriverDtoRequest driverDto);
    DriverDtoResponse getById(String cin);
    List<DriverDtoResponse> getAllDrivers();
    List<DriverDtoResponse> getAvailableDriversBetweenDates(LocalDateTime start, LocalDateTime end);
    List<TripDtoResponse> getTripsOfDriver(String cin);
    DriverUserDtoResponse updateUserAccountForDriver(String cin, UserDtoRequest userDtoRequest);
    void deleteDriverUserAccount(String cin);
    DriverDtoResponse update(String cin, DriverDtoRequest driverDto);
    void delete(String cin);

    void assignUserToDriver(String cin, Long userId);
}
