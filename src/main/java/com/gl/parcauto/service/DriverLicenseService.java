package com.gl.parcauto.service;

import com.gl.parcauto.dto.request.DriverLicenseDtoRequest;
import com.gl.parcauto.dto.response.DriverLicenseDtoResponse;

import java.util.List;

public interface DriverLicenseService {
    DriverLicenseDtoResponse create(String cin, DriverLicenseDtoRequest driverLicenseDto);
    DriverLicenseDtoResponse getById(String cin, Long driverLicenseId);
    List<DriverLicenseDtoResponse> getLicensesForDriver(String cin);
    DriverLicenseDtoResponse update(String cin, Long licenseId, DriverLicenseDtoRequest driverLicense);
    void delete(String cin, Long licenseId);
}
