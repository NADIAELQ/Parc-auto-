package com.gl.parcauto.repository;

import com.gl.parcauto.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Long> {
    @Query("select t from Trip t where " +
            "(t.startDate >= :start_trip and t.endDate <= :end_trip)")
    List<Trip> findTripBetweenDates(
            @Param(value = "start_trip") LocalDateTime start,
            @Param(value = "end_trip") LocalDateTime end);
}