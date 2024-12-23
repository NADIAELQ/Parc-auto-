package com.gl.parcauto.repository;

import com.gl.parcauto.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface DriverRepository extends JpaRepository<Driver, String> {
    Boolean existsByCin(String cin);
    @Query(value = "select d from Driver d where not exists" +
            "(select t.driver from Trip t where t.driver = d and " +
            "((t.startDate <= :end_trip and t.endDate >= :start_trip ) or" +
            "(t.startDate >= :start_trip and t.endDate <= :end_trip ) or " +
            "(t.startDate <= :start_trip and t.endDate >= :start_trip ) or " +
            "(t.startDate <= :start_trip and t.endDate >= :end_trip)))")

    List<Driver> findDriversWithoutTripsBetweenDates(
            @Param(value = "start_trip") LocalDateTime start,
            @Param(value = "end_trip") LocalDateTime end);
}

// Boolean existsByCin