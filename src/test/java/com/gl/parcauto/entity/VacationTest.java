package com.gl.parcauto.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

class VacationTest {
    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link Vacation#Vacation()}
     *   <li>{@link Vacation#setDriver(Driver)}
     *   <li>{@link Vacation#setEnd(LocalDateTime)}
     *   <li>{@link Vacation#setId(Long)}
     *   <li>{@link Vacation#setStart(LocalDateTime)}
     *   <li>{@link Vacation#getDriver()}
     *   <li>{@link Vacation#getEnd()}
     *   <li>{@link Vacation#getId()}
     *   <li>{@link Vacation#getStart()}
     * </ul>
     */
    @Test
    void testConstructor() {
        Vacation actualVacation = new Vacation();
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
        actualVacation.setDriver(driver);
        LocalDateTime end = LocalDate.of(1970, 1, 1).atStartOfDay();
        actualVacation.setEnd(end);
        actualVacation.setId(1L);
        LocalDateTime start = LocalDate.of(1970, 1, 1).atStartOfDay();
        actualVacation.setStart(start);
        Driver actualDriver = actualVacation.getDriver();
        LocalDateTime actualEnd = actualVacation.getEnd();
        Long actualId = actualVacation.getId();
        LocalDateTime actualStart = actualVacation.getStart();
        assertEquals(1L, actualId.longValue());
        assertSame(driver, actualDriver);
        assertSame(end, actualEnd);
        assertSame(start, actualStart);
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link Vacation#Vacation(Long, LocalDateTime, LocalDateTime, Driver)}
     *   <li>{@link Vacation#setDriver(Driver)}
     *   <li>{@link Vacation#setEnd(LocalDateTime)}
     *   <li>{@link Vacation#setId(Long)}
     *   <li>{@link Vacation#setStart(LocalDateTime)}
     *   <li>{@link Vacation#getDriver()}
     *   <li>{@link Vacation#getEnd()}
     *   <li>{@link Vacation#getId()}
     *   <li>{@link Vacation#getStart()}
     * </ul>
     */
    @Test
    void testConstructor2() {
        LocalDateTime start = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime end = LocalDate.of(1970, 1, 1).atStartOfDay();

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
        Vacation actualVacation = new Vacation(1L, start, end, driver);
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
        actualVacation.setDriver(driver2);
        LocalDateTime end2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        actualVacation.setEnd(end2);
        actualVacation.setId(1L);
        LocalDateTime start2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        actualVacation.setStart(start2);
        Driver actualDriver = actualVacation.getDriver();
        LocalDateTime actualEnd = actualVacation.getEnd();
        Long actualId = actualVacation.getId();
        LocalDateTime actualStart = actualVacation.getStart();
        assertEquals(1L, actualId.longValue());
        assertSame(driver2, actualDriver);
        assertSame(end2, actualEnd);
        assertSame(start2, actualStart);
    }
}
