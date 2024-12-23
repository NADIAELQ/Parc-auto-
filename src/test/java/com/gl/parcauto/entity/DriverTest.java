package com.gl.parcauto.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DriverTest {
    @Test
    void testGetCin() {
        Driver driver = new Driver();
        driver.setCin("12345");
        assertEquals("12345", driver.getCin());
    }

    @Test
    void testSetCin() {
        Driver driver = new Driver();
        driver.setCin("12345");
        assertEquals("12345", driver.getCin());
    }

    @Test
    void testGetFirstName() {
        Driver driver = new Driver();
        driver.setFirstName("John");
        assertEquals("John", driver.getFirstName());
    }

    @Test
    void testSetFirstName() {
        Driver driver = new Driver();
        driver.setFirstName("John");
        assertEquals("John", driver.getFirstName());
    }

    @Test
    void testGetLastName() {
        Driver driver = new Driver();
        driver.setLastName("Doe");
        assertEquals("Doe", driver.getLastName());
    }

    @Test
    void testSetLastName() {
        Driver driver = new Driver();
        driver.setLastName("Doe");
        assertEquals("Doe", driver.getLastName());
    }

    @Test
    void testGetDateOfBirth() {
        Driver driver = new Driver();
        LocalDate dateOfBirth = LocalDate.of(1990, 1, 1);
        driver.setDateOfBirth(dateOfBirth);
        assertEquals(dateOfBirth, driver.getDateOfBirth());
    }

    @Test
    void testSetDateOfBirth() {
        Driver driver = new Driver();
        LocalDate dateOfBirth = LocalDate.of(1990, 1, 1);
        driver.setDateOfBirth(dateOfBirth);
        assertEquals(dateOfBirth, driver.getDateOfBirth());
    }

    @Test
    void testGetDriverLicenses() {
        Driver driver = new Driver();
        Set<DriverLicense> driverLicenses = new HashSet<>();
        // Add some driver licenses to the set
        driver.setDriverLicenses(driverLicenses);
        assertEquals(driverLicenses, driver.getDriverLicenses());
    }

    @Test
    void testSetDriverLicenses() {
        Driver driver = new Driver();
        Set<DriverLicense> driverLicenses = new HashSet<>();
        // Add some driver licenses to the set
        driver.setDriverLicenses(driverLicenses);
        assertEquals(driverLicenses, driver.getDriverLicenses());
    }
}
