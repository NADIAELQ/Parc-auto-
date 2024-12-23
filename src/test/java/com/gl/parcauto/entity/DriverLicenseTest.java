package com.gl.parcauto.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class DriverLicenseTest {
    @Test
    void testGetId() {
        DriverLicense license = new DriverLicense();
        license.setId(1L);
        assertEquals(1L, license.getId());
    }

    @Test
    void testSetId() {
        DriverLicense license = new DriverLicense();
        license.setId(1L);
        assertEquals(1L, license.getId());
    }

    @Test
    void testGetDeliveryDate() {
        DriverLicense license = new DriverLicense();
        LocalDateTime deliveryDate = LocalDateTime.now();
        license.setDeliveryDate(deliveryDate);
        assertEquals(deliveryDate, license.getDeliveryDate());
    }

    @Test
    void testSetDeliveryDate() {
        DriverLicense license = new DriverLicense();
        LocalDateTime deliveryDate = LocalDateTime.now();
        license.setDeliveryDate(deliveryDate);
        assertEquals(deliveryDate, license.getDeliveryDate());
    }

    @Test
    void testGetLicenseType() {
        DriverLicense license = new DriverLicense();
        license.setLicenseType(DriverLicenseType.A);
        assertEquals(DriverLicenseType.A, license.getLicenseType());
    }

    @Test
    void testSetLicenseType() {
        DriverLicense license = new DriverLicense();
        license.setLicenseType(DriverLicenseType.A);
        assertEquals(DriverLicenseType.A, license.getLicenseType());
    }

    @Test
    void testGetDriver() {
        DriverLicense license = new DriverLicense();
        Driver driver = new Driver();
        license.setDriver(driver);
        assertSame(driver, license.getDriver());
    }

    @Test
    void testSetDriver() {
        DriverLicense license = new DriverLicense();
        Driver driver = new Driver();
        license.setDriver(driver);
        assertSame(driver, license.getDriver());
    }

}
