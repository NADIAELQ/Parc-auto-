package com.gl.parcauto.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class AppExceptionTest {
    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link AppException#AppException(HttpStatus, String)}
     *   <li>{@link AppException#getMessage()}
     *   <li>{@link AppException#getStatus()}
     * </ul>
     */
    @Test
    void testConstructor() {
        AppException actualAppException = new AppException(HttpStatus.CONTINUE, "An error occurred");
        String actualMessage = actualAppException.getMessage();
        assertEquals("An error occurred", actualMessage);
        assertEquals(HttpStatus.CONTINUE, actualAppException.getStatus());
    }
}
