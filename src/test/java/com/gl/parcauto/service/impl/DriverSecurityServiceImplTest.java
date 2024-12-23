package com.gl.parcauto.service.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.gl.parcauto.auth.IAuthenticationFacade;
import com.gl.parcauto.entity.Driver;
import com.gl.parcauto.entity.Role;
import com.gl.parcauto.entity.User;
import com.gl.parcauto.repository.DriverRepository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {DriverSecurityServiceImpl.class})
@ExtendWith(SpringExtension.class)
class DriverSecurityServiceImplTest {
    @MockBean
    private DriverRepository driverRepository;

    @Autowired
    private DriverSecurityServiceImpl driverSecurityServiceImpl;

    @MockBean
    private IAuthenticationFacade iAuthenticationFacade;

    /**
     * Method under test: {@link DriverSecurityServiceImpl#isDriver(String)}
     */
    @Test
    void testIsDriver() {
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
        when(iAuthenticationFacade.getAuthentication())
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        boolean actualIsDriverResult = driverSecurityServiceImpl.isDriver("Cin");
        verify(iAuthenticationFacade).getAuthentication();
        verify(driverRepository).findById(Mockito.<String>any());
        assertFalse(actualIsDriverResult);
    }

    /**
     * Method under test: {@link DriverSecurityServiceImpl#isDriver(String)}
     */
    @Test
    void testIsDriver2() {
        Optional<Driver> emptyResult = Optional.empty();
        when(driverRepository.findById(Mockito.<String>any())).thenReturn(emptyResult);
        when(iAuthenticationFacade.getAuthentication())
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        boolean actualIsDriverResult = driverSecurityServiceImpl.isDriver("Cin");
        verify(iAuthenticationFacade).getAuthentication();
        verify(driverRepository).findById(Mockito.<String>any());
        assertFalse(actualIsDriverResult);
    }
}
