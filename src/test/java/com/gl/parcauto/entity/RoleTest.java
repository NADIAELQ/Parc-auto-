package com.gl.parcauto.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class RoleTest {
    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link Role#Role()}
     *   <li>{@link Role#setId(Long)}
     *   <li>{@link Role#setName(String)}
     *   <li>{@link Role#getId()}
     *   <li>{@link Role#getName()}
     * </ul>
     */
    @Test
    void testConstructor() {
        Role actualRole = new Role();
        actualRole.setId(1L);
        actualRole.setName("Name");
        Long actualId = actualRole.getId();
        assertEquals("Name", actualRole.getName());
        assertEquals(1L, actualId.longValue());
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link Role#Role(Long, String)}
     *   <li>{@link Role#setId(Long)}
     *   <li>{@link Role#setName(String)}
     *   <li>{@link Role#getId()}
     *   <li>{@link Role#getName()}
     * </ul>
     */
    @Test
    void testConstructor2() {
        Role actualRole = new Role(1L, "Name");
        actualRole.setId(1L);
        actualRole.setName("Name");
        Long actualId = actualRole.getId();
        assertEquals("Name", actualRole.getName());
        assertEquals(1L, actualId.longValue());
    }
}
