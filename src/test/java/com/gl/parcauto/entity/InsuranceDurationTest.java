package com.gl.parcauto.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class InsuranceDurationTest {
    /**
     * Method under test: {@link InsuranceDuration#getNumberOfMonth()}
     */
    @Test
    void testGetNumberOfMonth() {
        assertEquals(1, InsuranceDuration.valueOf("MONTH").getNumberOfMonth().intValue());
    }
}
