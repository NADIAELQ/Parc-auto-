package com.gl.parcauto.repository;

import com.gl.parcauto.entity.VehicleInsurance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleInsuranceRepository extends JpaRepository<VehicleInsurance, Long> {
}