package com.gl.parcauto.repository;

import com.gl.parcauto.entity.DriverLicense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverLicenseRepository extends JpaRepository<DriverLicense, Long> {
}