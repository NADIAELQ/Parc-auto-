package com.gl.parcauto.service;

import com.gl.parcauto.entity.DriverLicenseType;


public interface ConformityService {
    boolean hasDriverLicense(String driverId, DriverLicenseType licenseType);
    boolean isConformVehicle(String vehicleId, DriverLicenseType licenseType);
}
