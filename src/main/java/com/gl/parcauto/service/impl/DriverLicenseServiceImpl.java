package com.gl.parcauto.service.impl;

import com.gl.parcauto.dto.request.DriverLicenseDtoRequest;
import com.gl.parcauto.dto.response.DriverLicenseDtoResponse;
import com.gl.parcauto.entity.Driver;
import com.gl.parcauto.entity.DriverLicense;
import com.gl.parcauto.exception.AppException;
import com.gl.parcauto.exception.ResourceNotFoundException;
import com.gl.parcauto.mapper.DriverLicenseMapper;
import com.gl.parcauto.repository.DriverLicenseRepository;
import com.gl.parcauto.repository.DriverRepository;
import com.gl.parcauto.service.DriverLicenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DriverLicenseServiceImpl implements DriverLicenseService {
    private final DriverRepository driverRepository;
    private final DriverLicenseRepository driverLicenseRepository;
    @Override
    @Transactional
    public DriverLicenseDtoResponse create(String cin, DriverLicenseDtoRequest driverLicenseDto) {
        // Get driver from db
        Driver driver = driverRepository.findById(cin).orElseThrow(
                () -> new ResourceNotFoundException("Driver", "id", cin)
        );

        // DTO to entity
        DriverLicense driverLicense = DriverLicenseMapper.INSTANCE.licenseDtoRequestToDriverLicense(driverLicenseDto);

        // Add driver to driver license
        driverLicense.setDriver(driver);

        // Save driver license in database
        DriverLicense savedLicense = driverLicenseRepository.save(driverLicense);

        return DriverLicenseMapper.INSTANCE.licenseToDriverLicenseDtoResponse(savedLicense);
    }

    @Override
    public DriverLicenseDtoResponse getById(String cin, Long driverLicenseId) {
        // Get driver from db
        Driver driver = driverRepository.findById(cin).orElseThrow(
                () -> new ResourceNotFoundException("Driver", "id", cin)
        );

        // Get driver license from database
        DriverLicense driverLicense = driverLicenseRepository.findById(driverLicenseId).orElseThrow(
                () -> new ResourceNotFoundException("Driver license", "id", driverLicenseId.toString())
        );

        // Validate driver license for driver
        validateLicenseForDriver(driver, driverLicense);

        return DriverLicenseMapper.INSTANCE.licenseToDriverLicenseDtoResponse(driverLicense);
    }
    @Override
    public List<DriverLicenseDtoResponse> getLicensesForDriver(String cin) {
        // Get driver from database
        Driver driver = driverRepository.findById(cin).orElseThrow(
                () -> new ResourceNotFoundException("Driver", "id", cin)
        );

        return driver.getDriverLicenses().stream()
                .map(DriverLicenseMapper.INSTANCE::licenseToDriverLicenseDtoResponse)
                .collect(Collectors.toList());
    }

    @Override
    public DriverLicenseDtoResponse update(String cin, Long licenseId, DriverLicenseDtoRequest driverLicenseDto) {
        // Get driver from database
        Driver driver = driverRepository.findById(cin).orElseThrow(
                () -> new ResourceNotFoundException("Driver", "id", cin)
        );

        // Get driver license from database
        DriverLicense driverLicense = driverLicenseRepository.findById(licenseId).orElseThrow(
                () -> new ResourceNotFoundException("Driver license", "id", licenseId.toString())
        );

        // Validate driver license for driver
        validateLicenseForDriver(driver, driverLicense);


        if(driverLicenseDto.deliveryDate() != null) {
            driverLicense.setDeliveryDate(driverLicenseDto.deliveryDate());
        }

        if(driverLicenseDto.licenseType() != null) {
            driverLicense.setLicenseType(driverLicenseDto.licenseType());
        }

        // Update driver license in database
        DriverLicense updatedDriverLicense = driverLicenseRepository.save(driverLicense);

        return DriverLicenseMapper.INSTANCE.licenseToDriverLicenseDtoResponse(updatedDriverLicense);
    }

    @Override
    public void delete(String cin, Long licenseId) {
        // Get driver from database
        Driver driver = driverRepository.findById(cin).orElseThrow(
                () -> new ResourceNotFoundException("Driver", "id", cin)
        );

        // Get driver license from database
        DriverLicense driverLicense = driverLicenseRepository.findById(licenseId).orElseThrow(
                () -> new ResourceNotFoundException("Driver license", "id", licenseId.toString())
        );

        // Validate driver license for driver
        validateLicenseForDriver(driver, driverLicense);

        driverLicenseRepository.delete(driverLicense);
    }
    private void validateLicenseForDriver(Driver driver, DriverLicense driverLicense) {
        if(!driverLicense.getDriver().getCin().equals(driver.getCin()))
            throw new AppException(HttpStatus.NOT_FOUND, String.format("Driver license not found for driver with ID %s", driver.getCin()));
    }
}
