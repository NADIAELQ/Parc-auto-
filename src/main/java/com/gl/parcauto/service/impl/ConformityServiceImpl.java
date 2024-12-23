package com.gl.parcauto.service.impl;

import com.gl.parcauto.entity.Driver;
import com.gl.parcauto.entity.DriverLicense;
import com.gl.parcauto.entity.DriverLicenseType;
import com.gl.parcauto.entity.Vehicle;
import com.gl.parcauto.exception.ResourceNotFoundException;
import com.gl.parcauto.repository.DriverRepository;
import com.gl.parcauto.repository.VehicleRepository;
import com.gl.parcauto.service.ConformityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ConformityServiceImpl implements ConformityService {
    private final DriverRepository driverRepository;
    private final VehicleRepository vehicleRepository;
    @Override
    public boolean hasDriverLicense(String driverId, DriverLicenseType licenseType) {
        Objects.requireNonNull(licenseType, "License type cannot be null");
        Driver driver = driverRepository.findById(driverId).orElseThrow(
                () -> new ResourceNotFoundException("Driver", "id", driverId)
        );
        Set<DriverLicense> licenseTypes = driver.getDriverLicenses();

        return licenseTypes.stream().map(DriverLicense::getLicenseType).anyMatch(l -> l.equals(licenseType));
    }

    @Override
    public boolean isConformVehicle(String vehicleId, DriverLicenseType licenseType) {
        Objects.requireNonNull(licenseType, "License type cannot be null");

        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(
                () -> new ResourceNotFoundException("Vehicle", "id", vehicleId)
        );

        DriverLicenseType vehicleType = vehicle.getType();

        return vehicleType.equals(licenseType);
    }
}
