package com.gl.parcauto.service.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.gl.parcauto.entity.Driver;
import com.gl.parcauto.entity.DriverLicense;
import com.gl.parcauto.entity.DriverLicenseType;
import com.gl.parcauto.entity.Role;
import com.gl.parcauto.entity.User;
import com.gl.parcauto.exception.ResourceNotFoundException;
import com.gl.parcauto.repository.DriverRepository;
import com.gl.parcauto.repository.VehicleRepository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ConformityServiceImpl.class})
@ExtendWith(SpringExtension.class)
class ConformityServiceImplTest {
    @Autowired
    private ConformityServiceImpl conformityServiceImpl;

    @MockBean
    private DriverRepository driverRepository;

    @MockBean
    private VehicleRepository vehicleRepository;

    /**
     * Method under test:
     * {@link ConformityServiceImpl#hasDriverLicense(String, DriverLicenseType)}
     */
    @Test
    void testHasDriverLicense() {
        Role role = new Role();
        role.setId(1L);
        role.setName("Name");

        User user = new User();
        user.setId(1L);
        user.setPassword("iloveyou");
        user.setRole(role);
        user.setUsername("janedoe");

        Driver driver = new Driver();
        driver.setCin("Cin");
        driver.setDateOfBirth(LocalDate.of(1970, 1, 1));
        driver.setDriverLicenses(new HashSet<>());
        driver.setFirstName("Jane");
        driver.setLastName("Doe");
        driver.setTrips(new HashSet<>());
        driver.setUser(user);
        driver.setVacations(new HashSet<>());
        Optional<Driver> ofResult = Optional.of(driver);
        when(driverRepository.findById(Mockito.<String>any())).thenReturn(ofResult);
        boolean actualHasDriverLicenseResult = conformityServiceImpl.hasDriverLicense("42", DriverLicenseType.A);
        verify(driverRepository).findById(Mockito.<String>any());
        assertFalse(actualHasDriverLicenseResult);
    }

    /**
     * Method under test:
     * {@link ConformityServiceImpl#hasDriverLicense(String, DriverLicenseType)}
     */
    @Test
    void testHasDriverLicense2() {
        Role role = new Role();
        role.setId(1L);
        role.setName("License type cannot be null");

        User user = new User();
        user.setId(1L);
        user.setPassword("iloveyou");
        user.setRole(role);
        user.setUsername("janedoe");

        Driver driver = new Driver();
        driver.setCin("License type cannot be null");
        driver.setDateOfBirth(LocalDate.of(1970, 1, 1));
        driver.setDriverLicenses(new HashSet<>());
        driver.setFirstName("Jane");
        driver.setLastName("Doe");
        driver.setTrips(new HashSet<>());
        driver.setUser(user);
        driver.setVacations(new HashSet<>());

        DriverLicense driverLicense = new DriverLicense();
        driverLicense.setDeliveryDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        driverLicense.setDriver(driver);
        driverLicense.setId(1L);
        driverLicense.setLicenseType(DriverLicenseType.A);

        HashSet<DriverLicense> driverLicenses = new HashSet<>();
        driverLicenses.add(driverLicense);

        Role role2 = new Role();
        role2.setId(1L);
        role2.setName("Name");

        User user2 = new User();
        user2.setId(1L);
        user2.setPassword("iloveyou");
        user2.setRole(role2);
        user2.setUsername("janedoe");

        Driver driver2 = new Driver();
        driver2.setCin("Cin");
        driver2.setDateOfBirth(LocalDate.of(1970, 1, 1));
        driver2.setDriverLicenses(driverLicenses);
        driver2.setFirstName("Jane");
        driver2.setLastName("Doe");
        driver2.setTrips(new HashSet<>());
        driver2.setUser(user2);
        driver2.setVacations(new HashSet<>());
        Optional<Driver> ofResult = Optional.of(driver2);
        when(driverRepository.findById(Mockito.<String>any())).thenReturn(ofResult);
        boolean actualHasDriverLicenseResult = conformityServiceImpl.hasDriverLicense("42", DriverLicenseType.A);
        verify(driverRepository).findById(Mockito.<String>any());
        assertTrue(actualHasDriverLicenseResult);
    }

    /**
     * Method under test:
     * {@link ConformityServiceImpl#hasDriverLicense(String, DriverLicenseType)}
     */
    @Test
    void testHasDriverLicense3() {
        Role role = new Role();
        role.setId(1L);
        role.setName("License type cannot be null");

        User user = new User();
        user.setId(1L);
        user.setPassword("iloveyou");
        user.setRole(role);
        user.setUsername("janedoe");

        Driver driver = new Driver();
        driver.setCin("License type cannot be null");
        driver.setDateOfBirth(LocalDate.of(1970, 1, 1));
        driver.setDriverLicenses(new HashSet<>());
        driver.setFirstName("Jane");
        driver.setLastName("Doe");
        driver.setTrips(new HashSet<>());
        driver.setUser(user);
        driver.setVacations(new HashSet<>());

        DriverLicense driverLicense = new DriverLicense();
        driverLicense.setDeliveryDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        driverLicense.setDriver(driver);
        driverLicense.setId(1L);
        driverLicense.setLicenseType(DriverLicenseType.A);

        Role role2 = new Role();
        role2.setId(2L);
        role2.setName("Name");

        User user2 = new User();
        user2.setId(2L);
        user2.setPassword("License type cannot be null");
        user2.setRole(role2);
        user2.setUsername("License type cannot be null");

        Driver driver2 = new Driver();
        driver2.setCin("Cin");
        driver2.setDateOfBirth(LocalDate.of(1970, 1, 1));
        driver2.setDriverLicenses(new HashSet<>());
        driver2.setFirstName("John");
        driver2.setLastName("Smith");
        driver2.setTrips(new HashSet<>());
        driver2.setUser(user2);
        driver2.setVacations(new HashSet<>());

        DriverLicense driverLicense2 = new DriverLicense();
        driverLicense2.setDeliveryDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        driverLicense2.setDriver(driver2);
        driverLicense2.setId(2L);
        driverLicense2.setLicenseType(DriverLicenseType.B);

        HashSet<DriverLicense> driverLicenses = new HashSet<>();
        driverLicenses.add(driverLicense2);
        driverLicenses.add(driverLicense);

        Role role3 = new Role();
        role3.setId(1L);
        role3.setName("Name");

        User user3 = new User();
        user3.setId(1L);
        user3.setPassword("iloveyou");
        user3.setRole(role3);
        user3.setUsername("janedoe");

        Driver driver3 = new Driver();
        driver3.setCin("Cin");
        driver3.setDateOfBirth(LocalDate.of(1970, 1, 1));
        driver3.setDriverLicenses(driverLicenses);
        driver3.setFirstName("Jane");
        driver3.setLastName("Doe");
        driver3.setTrips(new HashSet<>());
        driver3.setUser(user3);
        driver3.setVacations(new HashSet<>());
        Optional<Driver> ofResult = Optional.of(driver3);
        when(driverRepository.findById(Mockito.<String>any())).thenReturn(ofResult);
        boolean actualHasDriverLicenseResult = conformityServiceImpl.hasDriverLicense("42", DriverLicenseType.A);
        verify(driverRepository).findById(Mockito.<String>any());
        assertTrue(actualHasDriverLicenseResult);
    }

    /**
     * Method under test:
     * {@link ConformityServiceImpl#hasDriverLicense(String, DriverLicenseType)}
     */
    @Test
    void testHasDriverLicense4() {
        Optional<Driver> emptyResult = Optional.empty();
        when(driverRepository.findById(Mockito.<String>any())).thenReturn(emptyResult);
        assertThrows(ResourceNotFoundException.class,
                () -> conformityServiceImpl.hasDriverLicense("42", DriverLicenseType.A));
        verify(driverRepository).findById(Mockito.<String>any());
    }

    /**
     * Method under test:
     * {@link ConformityServiceImpl#hasDriverLicense(String, DriverLicenseType)}
     */
    @Test
    void testHasDriverLicense5() {
        Role role = new Role();
        role.setId(1L);
        role.setName("Name");

        User user = new User();
        user.setId(1L);
        user.setPassword("iloveyou");
        user.setRole(role);
        user.setUsername("janedoe");

        Driver driver = new Driver();
        driver.setCin("Cin");
        driver.setDateOfBirth(LocalDate.of(1970, 1, 1));
        driver.setDriverLicenses(new HashSet<>());
        driver.setFirstName("Jane");
        driver.setLastName("Doe");
        driver.setTrips(new HashSet<>());
        driver.setUser(user);
        driver.setVacations(new HashSet<>());
        Optional<Driver> ofResult = Optional.of(driver);
        when(driverRepository.findById(Mockito.<String>any())).thenReturn(ofResult);
        boolean actualHasDriverLicenseResult = conformityServiceImpl.hasDriverLicense("42", DriverLicenseType.B);
        verify(driverRepository).findById(Mockito.<String>any());
        assertFalse(actualHasDriverLicenseResult);
    }

    /**
     * Method under test:
     * {@link ConformityServiceImpl#hasDriverLicense(String, DriverLicenseType)}
     */
    @Test
    void testHasDriverLicense6() {
        Role role = new Role();
        role.setId(1L);
        role.setName("Name");

        User user = new User();
        user.setId(1L);
        user.setPassword("iloveyou");
        user.setRole(role);
        user.setUsername("janedoe");

        Driver driver = new Driver();
        driver.setCin("Cin");
        driver.setDateOfBirth(LocalDate.of(1970, 1, 1));
        driver.setDriverLicenses(new HashSet<>());
        driver.setFirstName("Jane");
        driver.setLastName("Doe");
        driver.setTrips(new HashSet<>());
        driver.setUser(user);
        driver.setVacations(new HashSet<>());
        Optional<Driver> ofResult = Optional.of(driver);
        when(driverRepository.findById(Mockito.<String>any())).thenReturn(ofResult);
        boolean actualHasDriverLicenseResult = conformityServiceImpl.hasDriverLicense("42", DriverLicenseType.C);
        verify(driverRepository).findById(Mockito.<String>any());
        assertFalse(actualHasDriverLicenseResult);
    }

    /**
     * Method under test:
     * {@link ConformityServiceImpl#hasDriverLicense(String, DriverLicenseType)}
     */
    @Test
    void testHasDriverLicense7() {
        Role role = new Role();
        role.setId(1L);
        role.setName("Name");

        User user = new User();
        user.setId(1L);
        user.setPassword("iloveyou");
        user.setRole(role);
        user.setUsername("janedoe");

        Driver driver = new Driver();
        driver.setCin("Cin");
        driver.setDateOfBirth(LocalDate.of(1970, 1, 1));
        driver.setDriverLicenses(new HashSet<>());
        driver.setFirstName("Jane");
        driver.setLastName("Doe");
        driver.setTrips(new HashSet<>());
        driver.setUser(user);
        driver.setVacations(new HashSet<>());
        Optional<Driver> ofResult = Optional.of(driver);
        when(driverRepository.findById(Mockito.<String>any())).thenReturn(ofResult);
        boolean actualHasDriverLicenseResult = conformityServiceImpl.hasDriverLicense("42", DriverLicenseType.D);
        verify(driverRepository).findById(Mockito.<String>any());
        assertFalse(actualHasDriverLicenseResult);
    }

    /**
     * Method under test:
     * {@link ConformityServiceImpl#hasDriverLicense(String, DriverLicenseType)}
     */
    @Test
    void testHasDriverLicense8() {
        when(driverRepository.findById(Mockito.<String>any()))
                .thenThrow(new ResourceNotFoundException("License type cannot be null", "License type cannot be null", "42"));
        assertThrows(ResourceNotFoundException.class,
                () -> conformityServiceImpl.hasDriverLicense("42", DriverLicenseType.A));
        verify(driverRepository).findById(Mockito.<String>any());
    }
}
