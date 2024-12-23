package com.gl.parcauto.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.gl.parcauto.exception.AppException;
import com.gl.parcauto.repository.DriverRepository;
import com.gl.parcauto.repository.VacationRepository;
import com.gl.parcauto.repository.VehicleRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AvailabilityServiceImpl.class})
@ExtendWith(SpringExtension.class)
class AvailabilityServiceImplDiffblueTest {
    @Autowired
    private AvailabilityServiceImpl availabilityServiceImpl;

    @MockBean
    private DriverRepository driverRepository;

    @MockBean
    private VacationRepository vacationRepository;

    @MockBean
    private VehicleRepository vehicleRepository;

    /**
     * Method under test:
     * {@link AvailabilityServiceImpl#isAvailableDriver(String, LocalDateTime, LocalDateTime)}
     */
    @Test
    void testIsAvailableDriver() {
        LocalDateTime start = LocalDate.of(1970, 1, 1).atStartOfDay();
        assertThrows(AppException.class,
                () -> availabilityServiceImpl.isAvailableDriver("42", start, LocalDate.of(1970, 1, 1).atStartOfDay()));
    }

    /**
     * Method under test:
     * {@link AvailabilityServiceImpl#isAvailableVehicle(String, LocalDateTime, LocalDateTime)}
     */
    @Test
    void testIsAvailableVehicle() {
        LocalDateTime start = LocalDate.of(1970, 1, 1).atStartOfDay();
        assertThrows(AppException.class,
                () -> availabilityServiceImpl.isAvailableVehicle("42", start, LocalDate.of(1970, 1, 1).atStartOfDay()));
    }
}
