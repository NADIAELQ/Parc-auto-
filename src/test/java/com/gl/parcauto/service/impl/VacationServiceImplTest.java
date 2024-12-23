package com.gl.parcauto.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.gl.parcauto.dto.request.VacationDtoRequest;
import com.gl.parcauto.dto.response.VacationDtoResponse;
import com.gl.parcauto.entity.Driver;
import com.gl.parcauto.entity.DriverLicense;
import com.gl.parcauto.entity.Role;
import com.gl.parcauto.entity.Trip;
import com.gl.parcauto.entity.User;
import com.gl.parcauto.entity.Vacation;
import com.gl.parcauto.exception.AppException;
import com.gl.parcauto.exception.ResourceNotFoundException;
import com.gl.parcauto.repository.DriverRepository;
import com.gl.parcauto.repository.VacationRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {VacationServiceImpl.class})
@ExtendWith(SpringExtension.class)
class VacationServiceImplTest {
    @MockBean
    private DriverRepository driverRepository;

    @MockBean
    private VacationRepository vacationRepository;

    @Autowired
    private VacationServiceImpl vacationServiceImpl;

    /**
     * Method under test:
     * {@link VacationServiceImpl#create(String, VacationDtoRequest)}
     */
    @Test
    void testCreate() {
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

        Vacation vacation = new Vacation();
        vacation.setDriver(driver);
        vacation.setEnd(LocalDate.of(1970, 1, 1).atStartOfDay());
        vacation.setId(1L);
        vacation.setStart(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(vacationRepository.save(Mockito.<Vacation>any())).thenReturn(vacation);

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
        driver2.setDriverLicenses(new HashSet<>());
        driver2.setFirstName("Jane");
        driver2.setLastName("Doe");
        driver2.setTrips(new HashSet<>());
        driver2.setUser(user2);
        driver2.setVacations(new HashSet<>());
        Optional<Driver> ofResult = Optional.of(driver2);
        when(driverRepository.findById(Mockito.<String>any())).thenReturn(ofResult);
        LocalDateTime start = LocalDate.of(1970, 1, 1).atStartOfDay();
        VacationDtoResponse actualCreateResult = vacationServiceImpl.create("Cin",
                new VacationDtoRequest(start, LocalDate.of(1970, 1, 1).atStartOfDay()));
        verify(driverRepository).findById(Mockito.<String>any());
        verify(vacationRepository).save(Mockito.<Vacation>any());
        assertEquals("00:00", actualCreateResult.start().toLocalTime().toString());
        assertEquals("1970-01-01", actualCreateResult.end().toLocalDate().toString());
        assertEquals("Cin", actualCreateResult.driverCin());
        assertEquals(1L, actualCreateResult.id().longValue());
    }

    /**
     * Method under test:
     * {@link VacationServiceImpl#create(String, VacationDtoRequest)}
     */
    @Test
    void testCreate2() {
        when(vacationRepository.save(Mockito.<Vacation>any()))
                .thenThrow(new AppException(HttpStatus.CONTINUE, "An error occurred"));

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
        LocalDateTime start = LocalDate.of(1970, 1, 1).atStartOfDay();
        assertThrows(AppException.class, () -> vacationServiceImpl.create("Cin",
                new VacationDtoRequest(start, LocalDate.of(1970, 1, 1).atStartOfDay())));
        verify(driverRepository).findById(Mockito.<String>any());
        verify(vacationRepository).save(Mockito.<Vacation>any());
    }

    /**
     * Method under test:
     * {@link VacationServiceImpl#create(String, VacationDtoRequest)}
     */
    @Test
    void testCreate3() {
        Optional<Driver> emptyResult = Optional.empty();
        when(driverRepository.findById(Mockito.<String>any())).thenReturn(emptyResult);
        LocalDateTime start = LocalDate.of(1970, 1, 1).atStartOfDay();
        assertThrows(ResourceNotFoundException.class, () -> vacationServiceImpl.create("Cin",
                new VacationDtoRequest(start, LocalDate.of(1970, 1, 1).atStartOfDay())));
        verify(driverRepository).findById(Mockito.<String>any());
    }

    /**
     * Method under test: {@link VacationServiceImpl#getById(String, Long)}
     */
    @Test
    void testGetById() {
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

        Vacation vacation = new Vacation();
        vacation.setDriver(driver);
        vacation.setEnd(LocalDate.of(1970, 1, 1).atStartOfDay());
        vacation.setId(1L);
        vacation.setStart(LocalDate.of(1970, 1, 1).atStartOfDay());
        Optional<Vacation> ofResult = Optional.of(vacation);
        when(vacationRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

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
        driver2.setDriverLicenses(new HashSet<>());
        driver2.setFirstName("Jane");
        driver2.setLastName("Doe");
        driver2.setTrips(new HashSet<>());
        driver2.setUser(user2);
        driver2.setVacations(new HashSet<>());
        Optional<Driver> ofResult2 = Optional.of(driver2);
        when(driverRepository.findById(Mockito.<String>any())).thenReturn(ofResult2);
        VacationDtoResponse actualById = vacationServiceImpl.getById("Cin", 1L);
        verify(driverRepository).findById(Mockito.<String>any());
        verify(vacationRepository).findById(Mockito.<Long>any());
        assertEquals("00:00", actualById.start().toLocalTime().toString());
        assertEquals("1970-01-01", actualById.end().toLocalDate().toString());
        assertEquals("Cin", actualById.driverCin());
        assertEquals(1L, actualById.id().longValue());
    }

    /**
     * Method under test: {@link VacationServiceImpl#getById(String, Long)}
     */
    @Test
    void testGetById2() {
        when(vacationRepository.findById(Mockito.<Long>any()))
                .thenThrow(new AppException(HttpStatus.CONTINUE, "An error occurred"));

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
        assertThrows(AppException.class, () -> vacationServiceImpl.getById("Cin", 1L));
        verify(driverRepository).findById(Mockito.<String>any());
        verify(vacationRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link VacationServiceImpl#getById(String, Long)}
     */
    @Test
    void testGetById3() {
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

        Role role2 = new Role();
        role2.setId(1L);
        role2.setName("Name");

        User user2 = new User();
        user2.setId(1L);
        user2.setPassword("iloveyou");
        user2.setRole(role2);
        user2.setUsername("janedoe");
        Driver driver2 = mock(Driver.class);
        when(driver2.getCin()).thenReturn("foo");
        doNothing().when(driver2).setCin(Mockito.<String>any());
        doNothing().when(driver2).setDateOfBirth(Mockito.<LocalDate>any());
        doNothing().when(driver2).setDriverLicenses(Mockito.<Set<DriverLicense>>any());
        doNothing().when(driver2).setFirstName(Mockito.<String>any());
        doNothing().when(driver2).setLastName(Mockito.<String>any());
        doNothing().when(driver2).setTrips(Mockito.<Set<Trip>>any());
        doNothing().when(driver2).setUser(Mockito.<User>any());
        doNothing().when(driver2).setVacations(Mockito.<Set<Vacation>>any());
        driver2.setCin("Cin");
        driver2.setDateOfBirth(LocalDate.of(1970, 1, 1));
        driver2.setDriverLicenses(new HashSet<>());
        driver2.setFirstName("Jane");
        driver2.setLastName("Doe");
        driver2.setTrips(new HashSet<>());
        driver2.setUser(user2);
        driver2.setVacations(new HashSet<>());
        Vacation vacation = mock(Vacation.class);
        when(vacation.getDriver()).thenReturn(driver2);
        doNothing().when(vacation).setDriver(Mockito.<Driver>any());
        doNothing().when(vacation).setEnd(Mockito.<LocalDateTime>any());
        doNothing().when(vacation).setId(Mockito.<Long>any());
        doNothing().when(vacation).setStart(Mockito.<LocalDateTime>any());
        vacation.setDriver(driver);
        vacation.setEnd(LocalDate.of(1970, 1, 1).atStartOfDay());
        vacation.setId(1L);
        vacation.setStart(LocalDate.of(1970, 1, 1).atStartOfDay());
        Optional<Vacation> ofResult = Optional.of(vacation);
        when(vacationRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

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
        driver3.setDriverLicenses(new HashSet<>());
        driver3.setFirstName("Jane");
        driver3.setLastName("Doe");
        driver3.setTrips(new HashSet<>());
        driver3.setUser(user3);
        driver3.setVacations(new HashSet<>());
        Optional<Driver> ofResult2 = Optional.of(driver3);
        when(driverRepository.findById(Mockito.<String>any())).thenReturn(ofResult2);
        assertThrows(AppException.class, () -> vacationServiceImpl.getById("Cin", 1L));
        verify(driver2).getCin();
        verify(driver2).setCin(Mockito.<String>any());
        verify(driver2).setDateOfBirth(Mockito.<LocalDate>any());
        verify(driver2).setDriverLicenses(Mockito.<Set<DriverLicense>>any());
        verify(driver2).setFirstName(Mockito.<String>any());
        verify(driver2).setLastName(Mockito.<String>any());
        verify(driver2).setTrips(Mockito.<Set<Trip>>any());
        verify(driver2).setUser(Mockito.<User>any());
        verify(driver2).setVacations(Mockito.<Set<Vacation>>any());
        verify(vacation).getDriver();
        verify(vacation).setDriver(Mockito.<Driver>any());
        verify(vacation).setEnd(Mockito.<LocalDateTime>any());
        verify(vacation).setId(Mockito.<Long>any());
        verify(vacation).setStart(Mockito.<LocalDateTime>any());
        verify(driverRepository).findById(Mockito.<String>any());
        verify(vacationRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link VacationServiceImpl#getVacationsForDriver(String)}
     */
    @Test
    void testGetVacationsForDriver() {
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
        List<VacationDtoResponse> actualVacationsForDriver = vacationServiceImpl.getVacationsForDriver("Cin");
        verify(driverRepository).findById(Mockito.<String>any());
        assertTrue(actualVacationsForDriver.isEmpty());
    }

    /**
     * Method under test: {@link VacationServiceImpl#getVacationsForDriver(String)}
     */
    @Test
    void testGetVacationsForDriver2() {
        Optional<Driver> emptyResult = Optional.empty();
        when(driverRepository.findById(Mockito.<String>any())).thenReturn(emptyResult);
        assertThrows(ResourceNotFoundException.class, () -> vacationServiceImpl.getVacationsForDriver("Cin"));
        verify(driverRepository).findById(Mockito.<String>any());
    }

    /**
     * Method under test: {@link VacationServiceImpl#getVacationsForDriver(String)}
     */
    @Test
    void testGetVacationsForDriver3() {
        when(driverRepository.findById(Mockito.<String>any()))
                .thenThrow(new AppException(HttpStatus.CONTINUE, "An error occurred"));
        assertThrows(AppException.class, () -> vacationServiceImpl.getVacationsForDriver("Cin"));
        verify(driverRepository).findById(Mockito.<String>any());
    }

    /**
     * Method under test:
     * {@link VacationServiceImpl#update(String, Long, VacationDtoRequest)}
     */
    @Test
    void testUpdate() {
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

        Vacation vacation = new Vacation();
        vacation.setDriver(driver);
        vacation.setEnd(LocalDate.of(1970, 1, 1).atStartOfDay());
        vacation.setId(1L);
        vacation.setStart(LocalDate.of(1970, 1, 1).atStartOfDay());
        Optional<Vacation> ofResult = Optional.of(vacation);

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
        driver2.setDriverLicenses(new HashSet<>());
        driver2.setFirstName("Jane");
        driver2.setLastName("Doe");
        driver2.setTrips(new HashSet<>());
        driver2.setUser(user2);
        driver2.setVacations(new HashSet<>());

        Vacation vacation2 = new Vacation();
        vacation2.setDriver(driver2);
        vacation2.setEnd(LocalDate.of(1970, 1, 1).atStartOfDay());
        vacation2.setId(1L);
        vacation2.setStart(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(vacationRepository.save(Mockito.<Vacation>any())).thenReturn(vacation2);
        when(vacationRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

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
        driver3.setDriverLicenses(new HashSet<>());
        driver3.setFirstName("Jane");
        driver3.setLastName("Doe");
        driver3.setTrips(new HashSet<>());
        driver3.setUser(user3);
        driver3.setVacations(new HashSet<>());
        Optional<Driver> ofResult2 = Optional.of(driver3);
        when(driverRepository.findById(Mockito.<String>any())).thenReturn(ofResult2);
        LocalDateTime start = LocalDate.of(1970, 1, 1).atStartOfDay();
        VacationDtoResponse actualUpdateResult = vacationServiceImpl.update("Cin", 1L,
                new VacationDtoRequest(start, LocalDate.of(1970, 1, 1).atStartOfDay()));
        verify(driverRepository).findById(Mockito.<String>any());
        verify(vacationRepository).findById(Mockito.<Long>any());
        verify(vacationRepository).save(Mockito.<Vacation>any());
        assertEquals("00:00", actualUpdateResult.start().toLocalTime().toString());
        assertEquals("1970-01-01", actualUpdateResult.end().toLocalDate().toString());
        assertEquals("Cin", actualUpdateResult.driverCin());
        assertEquals(1L, actualUpdateResult.id().longValue());
    }

    /**
     * Method under test:
     * {@link VacationServiceImpl#update(String, Long, VacationDtoRequest)}
     */
    @Test
    void testUpdate2() {
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

        Vacation vacation = new Vacation();
        vacation.setDriver(driver);
        vacation.setEnd(LocalDate.of(1970, 1, 1).atStartOfDay());
        vacation.setId(1L);
        vacation.setStart(LocalDate.of(1970, 1, 1).atStartOfDay());
        Optional<Vacation> ofResult = Optional.of(vacation);
        when(vacationRepository.save(Mockito.<Vacation>any()))
                .thenThrow(new AppException(HttpStatus.CONTINUE, "An error occurred"));
        when(vacationRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

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
        driver2.setDriverLicenses(new HashSet<>());
        driver2.setFirstName("Jane");
        driver2.setLastName("Doe");
        driver2.setTrips(new HashSet<>());
        driver2.setUser(user2);
        driver2.setVacations(new HashSet<>());
        Optional<Driver> ofResult2 = Optional.of(driver2);
        when(driverRepository.findById(Mockito.<String>any())).thenReturn(ofResult2);
        LocalDateTime start = LocalDate.of(1970, 1, 1).atStartOfDay();
        assertThrows(AppException.class, () -> vacationServiceImpl.update("Cin", 1L,
                new VacationDtoRequest(start, LocalDate.of(1970, 1, 1).atStartOfDay())));
        verify(driverRepository).findById(Mockito.<String>any());
        verify(vacationRepository).findById(Mockito.<Long>any());
        verify(vacationRepository).save(Mockito.<Vacation>any());
    }

    /**
     * Method under test: {@link VacationServiceImpl#delete(String, Long)}
     */
    @Test
    void testDelete() {
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

        Vacation vacation = new Vacation();
        vacation.setDriver(driver);
        vacation.setEnd(LocalDate.of(1970, 1, 1).atStartOfDay());
        vacation.setId(1L);
        vacation.setStart(LocalDate.of(1970, 1, 1).atStartOfDay());
        Optional<Vacation> ofResult = Optional.of(vacation);
        doNothing().when(vacationRepository).delete(Mockito.<Vacation>any());
        when(vacationRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

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
        driver2.setDriverLicenses(new HashSet<>());
        driver2.setFirstName("Jane");
        driver2.setLastName("Doe");
        driver2.setTrips(new HashSet<>());
        driver2.setUser(user2);
        driver2.setVacations(new HashSet<>());
        Optional<Driver> ofResult2 = Optional.of(driver2);
        when(driverRepository.findById(Mockito.<String>any())).thenReturn(ofResult2);
        vacationServiceImpl.delete("Cin", 1L);
        verify(vacationRepository).delete(Mockito.<Vacation>any());
        verify(driverRepository).findById(Mockito.<String>any());
        verify(vacationRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link VacationServiceImpl#delete(String, Long)}
     */
    @Test
    void testDelete2() {
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

        Vacation vacation = new Vacation();
        vacation.setDriver(driver);
        vacation.setEnd(LocalDate.of(1970, 1, 1).atStartOfDay());
        vacation.setId(1L);
        vacation.setStart(LocalDate.of(1970, 1, 1).atStartOfDay());
        Optional<Vacation> ofResult = Optional.of(vacation);
        doThrow(new AppException(HttpStatus.CONTINUE, "An error occurred")).when(vacationRepository)
                .delete(Mockito.<Vacation>any());
        when(vacationRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

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
        driver2.setDriverLicenses(new HashSet<>());
        driver2.setFirstName("Jane");
        driver2.setLastName("Doe");
        driver2.setTrips(new HashSet<>());
        driver2.setUser(user2);
        driver2.setVacations(new HashSet<>());
        Optional<Driver> ofResult2 = Optional.of(driver2);
        when(driverRepository.findById(Mockito.<String>any())).thenReturn(ofResult2);
        assertThrows(AppException.class, () -> vacationServiceImpl.delete("Cin", 1L));
        verify(vacationRepository).delete(Mockito.<Vacation>any());
        verify(driverRepository).findById(Mockito.<String>any());
        verify(vacationRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link VacationServiceImpl#delete(String, Long)}
     */
    @Test
    void testDelete3() {
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

        Role role2 = new Role();
        role2.setId(1L);
        role2.setName("Name");

        User user2 = new User();
        user2.setId(1L);
        user2.setPassword("iloveyou");
        user2.setRole(role2);
        user2.setUsername("janedoe");
        Driver driver2 = mock(Driver.class);
        when(driver2.getCin()).thenReturn("foo");
        doNothing().when(driver2).setCin(Mockito.<String>any());
        doNothing().when(driver2).setDateOfBirth(Mockito.<LocalDate>any());
        doNothing().when(driver2).setDriverLicenses(Mockito.<Set<DriverLicense>>any());
        doNothing().when(driver2).setFirstName(Mockito.<String>any());
        doNothing().when(driver2).setLastName(Mockito.<String>any());
        doNothing().when(driver2).setTrips(Mockito.<Set<Trip>>any());
        doNothing().when(driver2).setUser(Mockito.<User>any());
        doNothing().when(driver2).setVacations(Mockito.<Set<Vacation>>any());
        driver2.setCin("Cin");
        driver2.setDateOfBirth(LocalDate.of(1970, 1, 1));
        driver2.setDriverLicenses(new HashSet<>());
        driver2.setFirstName("Jane");
        driver2.setLastName("Doe");
        driver2.setTrips(new HashSet<>());
        driver2.setUser(user2);
        driver2.setVacations(new HashSet<>());
        Vacation vacation = mock(Vacation.class);
        when(vacation.getDriver()).thenReturn(driver2);
        doNothing().when(vacation).setDriver(Mockito.<Driver>any());
        doNothing().when(vacation).setEnd(Mockito.<LocalDateTime>any());
        doNothing().when(vacation).setId(Mockito.<Long>any());
        doNothing().when(vacation).setStart(Mockito.<LocalDateTime>any());
        vacation.setDriver(driver);
        vacation.setEnd(LocalDate.of(1970, 1, 1).atStartOfDay());
        vacation.setId(1L);
        vacation.setStart(LocalDate.of(1970, 1, 1).atStartOfDay());
        Optional<Vacation> ofResult = Optional.of(vacation);
        when(vacationRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

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
        driver3.setDriverLicenses(new HashSet<>());
        driver3.setFirstName("Jane");
        driver3.setLastName("Doe");
        driver3.setTrips(new HashSet<>());
        driver3.setUser(user3);
        driver3.setVacations(new HashSet<>());
        Optional<Driver> ofResult2 = Optional.of(driver3);
        when(driverRepository.findById(Mockito.<String>any())).thenReturn(ofResult2);
        assertThrows(AppException.class, () -> vacationServiceImpl.delete("Cin", 1L));
        verify(driver2).getCin();
        verify(driver2).setCin(Mockito.<String>any());
        verify(driver2).setDateOfBirth(Mockito.<LocalDate>any());
        verify(driver2).setDriverLicenses(Mockito.<Set<DriverLicense>>any());
        verify(driver2).setFirstName(Mockito.<String>any());
        verify(driver2).setLastName(Mockito.<String>any());
        verify(driver2).setTrips(Mockito.<Set<Trip>>any());
        verify(driver2).setUser(Mockito.<User>any());
        verify(driver2).setVacations(Mockito.<Set<Vacation>>any());
        verify(vacation).getDriver();
        verify(vacation).setDriver(Mockito.<Driver>any());
        verify(vacation).setEnd(Mockito.<LocalDateTime>any());
        verify(vacation).setId(Mockito.<Long>any());
        verify(vacation).setStart(Mockito.<LocalDateTime>any());
        verify(driverRepository).findById(Mockito.<String>any());
        verify(vacationRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link VacationServiceImpl#delete(String, Long)}
     */
    @Test
    void testDelete4() {
        Optional<Vacation> emptyResult = Optional.empty();
        when(vacationRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

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
        assertThrows(ResourceNotFoundException.class, () -> vacationServiceImpl.delete("Cin", 1L));
        verify(driverRepository).findById(Mockito.<String>any());
        verify(vacationRepository).findById(Mockito.<Long>any());
    }
}
