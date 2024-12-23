package com.gl.parcauto.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {JwtAuthEntryPoint.class})
@ExtendWith(SpringExtension.class)
class JwtAuthEntryPointTest {
    @Autowired
    private JwtAuthEntryPoint jwtAuthEntryPoint;

    /**
     * Method under test:
     * {@link JwtAuthEntryPoint#commence(HttpServletRequest, HttpServletResponse, AuthenticationException)}
     */
    @Test
    void testCommence() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        jwtAuthEntryPoint.commence(request, response, new AccountExpiredException("Msg"));
        assertEquals("Invalid credentials", response.getErrorMessage());
        assertEquals(401, response.getStatus());
        assertTrue(response.isCommitted());
    }
}
