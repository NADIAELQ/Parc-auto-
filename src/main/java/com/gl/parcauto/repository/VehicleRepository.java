package com.gl.parcauto.repository;

import com.gl.parcauto.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, String> {
    boolean existsByLicensePlate(String licensePlate);
    @Query(value = "select v from Vehicle v where not exists" +
            "(select t.vehicle from Trip t where t.vehicle = v and " +
            "((t.startDate <= :end_trip and t.endDate >= :start_trip ) or" +
            "(t.startDate >= :start_trip and t.endDate <= :end_trip ) or " +
            "(t.startDate <= :start_trip and t.endDate >= :start_trip ) or " +
            "(t.startDate <= :start_trip and t.endDate >= :end_trip)))")
    List<Vehicle> findVehiclesWithoutTripsBetweenDates(
            @Param(value = "start_trip") LocalDateTime start,
            @Param(value = "end_trip") LocalDateTime end);

}