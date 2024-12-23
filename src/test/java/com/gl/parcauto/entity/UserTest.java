package com.gl.parcauto.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

class UserTest {
    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link User#User()}
     *   <li>{@link User#setId(Long)}
     *   <li>{@link User#setPassword(String)}
     *   <li>{@link User#setRole(Role)}
     *   <li>{@link User#setUsername(String)}
     *   <li>{@link User#getId()}
     *   <li>{@link User#getPassword()}
     *   <li>{@link User#getRole()}
     *   <li>{@link User#getUsername()}
     * </ul>
     */
    @Test
    void testConstructor() {
        User actualUser = new User();
        actualUser.setId(1L);
        actualUser.setPassword("iloveyou");
        Role role = new Role();
        role.setId(1L);
        role.setName("Name");
        actualUser.setRole(role);
        actualUser.setUsername("janedoe");
        Long actualId = actualUser.getId();
        String actualPassword = actualUser.getPassword();
        Role actualRole = actualUser.getRole();
        assertEquals("iloveyou", actualPassword);
        assertEquals("janedoe", actualUser.getUsername());
        assertEquals(1L, actualId.longValue());
        assertSame(role, actualRole);
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link User#User(Long, String, String, Role)}
     *   <li>{@link User#setId(Long)}
     *   <li>{@link User#setPassword(String)}
     *   <li>{@link User#setRole(Role)}
     *   <li>{@link User#setUsername(String)}
     *   <li>{@link User#getId()}
     *   <li>{@link User#getPassword()}
     *   <li>{@link User#getRole()}
     *   <li>{@link User#getUsername()}
     * </ul>
     */
    @Test
    void testConstructor2() {
        Role role = new Role();
        role.setId(1L);
        role.setName("Name");
        User actualUser = new User(1L, "janedoe", "iloveyou", role);
        actualUser.setId(1L);
        actualUser.setPassword("iloveyou");
        Role role2 = new Role();
        role2.setId(1L);
        role2.setName("Name");
        actualUser.setRole(role2);
        actualUser.setUsername("janedoe");
        Long actualId = actualUser.getId();
        String actualPassword = actualUser.getPassword();
        Role actualRole = actualUser.getRole();
        assertEquals("iloveyou", actualPassword);
        assertEquals("janedoe", actualUser.getUsername());
        assertEquals(1L, actualId.longValue());
        assertSame(role2, actualRole);
    }
}
