package com.gl.parcauto.entity;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VehicleTest {
    @Test
    void testLicensePlateGetterAndSetter() {
        Vehicle vehicle = new Vehicle();
        vehicle.setLicensePlate("ABC123");
        assertEquals("ABC123", vehicle.getLicensePlate());
    }

    @Test
    void testYearGetterAndSetter() {
        Vehicle vehicle = new Vehicle();
        vehicle.setYear(2022);
        assertEquals(2022, vehicle.getYear());
    }

    // Add similar test methods for other getters and setters

    @Test
    void testTechnicalVisitsGetterAndSetter() {
        Vehicle vehicle = new Vehicle();
        TechnicalVisit visit1 = new TechnicalVisit();
        TechnicalVisit visit2 = new TechnicalVisit();

        Set<TechnicalVisit> technicalVisits = new HashSet<>();
        technicalVisits.add(visit1);
        technicalVisits.add(visit2);

        vehicle.setTechnicalVisits(technicalVisits);
        assertEquals(technicalVisits, vehicle.getTechnicalVisits());
    }

    @Test
    void testTripSetGetterAndSetter() {
        Vehicle vehicle = new Vehicle();
        Trip trip1 = new Trip();
        Trip trip2 = new Trip();

        Set<Trip> tripSet = new HashSet<>();
        tripSet.add(trip1);
        tripSet.add(trip2);

        vehicle.setTripSet(tripSet);
        assertEquals(tripSet, vehicle.getTripSet());
    }

}
