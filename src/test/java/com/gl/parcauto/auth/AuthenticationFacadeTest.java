package com.gl.parcauto.auth;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AuthenticationFacade.class})
@ExtendWith(SpringExtension.class)
class AuthenticationFacadeTest {
    @Autowired
    private AuthenticationFacade authenticationFacade;

    /**
     * Method under test: {@link AuthenticationFacade#getAuthentication()}
     */
    @Test
    void testGetAuthentication() {
        assertNull(authenticationFacade.getAuthentication());
    }
}
