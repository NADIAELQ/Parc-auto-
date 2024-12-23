package com.gl.parcauto.repository;

import com.gl.parcauto.entity.Vacation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VacationRepository extends JpaRepository<Vacation, Long> {
    @Query("select v from Vacation v where v.driver.cin = :driver_id order by v.id desc limit 1")
    Optional<Vacation> findFirstByDriverOrderByDescId(@Param(value = "driver_id") String driver_id);
}