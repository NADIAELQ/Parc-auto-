package com.gl.parcauto.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class VignetteTest {
    @Test
    void testGetId() {
        Vignette vignette = new Vignette();
        vignette.setId(1L);
        assertEquals(1L, vignette.getId());
    }

    @Test
    void testSetId() {
        Vignette vignette = new Vignette();
        vignette.setId(1L);
        assertEquals(1L, vignette.getId());
    }

    @Test
    void testGetDeliveryDate() {
        Vignette vignette = new Vignette();
        LocalDate deliveryDate = LocalDate.now();
        vignette.setDeliveryDate(deliveryDate);
        assertEquals(deliveryDate, vignette.getDeliveryDate());
    }

    @Test
    void testSetDeliveryDate() {
        Vignette vignette = new Vignette();
        LocalDate deliveryDate = LocalDate.now();
        vignette.setDeliveryDate(deliveryDate);
        assertEquals(deliveryDate, vignette.getDeliveryDate());
    }

    @Test
    void testGetVehicle() {
        Vignette vignette = new Vignette();
        Vehicle vehicle = new Vehicle();
        vignette.setVehicle(vehicle);
        assertSame(vehicle, vignette.getVehicle());
    }

    @Test
    void testSetVehicle() {
        Vignette vignette = new Vignette();
        Vehicle vehicle = new Vehicle();
        vignette.setVehicle(vehicle);
        assertSame(vehicle, vignette.getVehicle());
    }
}
