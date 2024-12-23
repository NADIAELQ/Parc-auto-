package com.gl.parcauto.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.gl.parcauto.entity.Role;

import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserModel.class, String.class})
@ExtendWith(SpringExtension.class)
class UserModelTest {
    @MockBean
    private Role role;

    @Autowired
    private UserModel userModel;

    /**
     * Method under test: {@link UserModel#getAuthorities()}
     */
    @Test
    void testGetAuthorities() {
        when(role.getName()).thenReturn("Name");
        Collection<? extends GrantedAuthority> actualAuthorities = userModel.getAuthorities();
        verify(role).getName();
        assertEquals(1, actualAuthorities.size());
        assertEquals("Name", ((List<? extends GrantedAuthority>) actualAuthorities).get(0).getAuthority());
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link UserModel#getPassword()}
     *   <li>{@link UserModel#getUsername()}
     *   <li>{@link UserModel#isAccountNonExpired()}
     *   <li>{@link UserModel#isAccountNonLocked()}
     *   <li>{@link UserModel#isCredentialsNonExpired()}
     *   <li>{@link UserModel#isEnabled()}
     * </ul>
     */
    @Test
    void testGetPassword() {
        Role role = new Role();
        role.setId(1L);
        role.setName("Name");
        UserModel userModel = new UserModel("janedoe", "iloveyou", role);
        String actualPassword = userModel.getPassword();
        String actualUsername = userModel.getUsername();
        boolean actualIsAccountNonExpiredResult = userModel.isAccountNonExpired();
        boolean actualIsAccountNonLockedResult = userModel.isAccountNonLocked();
        boolean actualIsCredentialsNonExpiredResult = userModel.isCredentialsNonExpired();
        assertEquals("iloveyou", actualPassword);
        assertEquals("janedoe", actualUsername);
        assertTrue(actualIsAccountNonExpiredResult);
        assertTrue(actualIsAccountNonLockedResult);
        assertTrue(actualIsCredentialsNonExpiredResult);
        assertTrue(userModel.isEnabled());
    }
}
