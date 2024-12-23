package com.gl.parcauto.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.gl.parcauto.auth.IAuthenticationFacade;
import com.gl.parcauto.auth.JwtTokenProvider;
import com.gl.parcauto.dto.request.LoginRequestDto;
import com.gl.parcauto.dto.request.RegisterDtoRequest;
import com.gl.parcauto.dto.request.ResetPasswordDtoRequest;
import com.gl.parcauto.dto.request.RoleDtoRequest;
import com.gl.parcauto.entity.Role;
import com.gl.parcauto.entity.User;
import com.gl.parcauto.exception.AppException;
import com.gl.parcauto.exception.ResourceNotFoundException;
import com.gl.parcauto.repository.RoleRepository;
import com.gl.parcauto.repository.UserRepository;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AuthServiceImpl.class})
@ExtendWith(SpringExtension.class)
class AuthServiceImplDiffblueTest {
    @Autowired
    private AuthServiceImpl authServiceImpl;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private IAuthenticationFacade iAuthenticationFacade;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private UserRepository userRepository;

    /**
     * Method under test: {@link AuthServiceImpl#login(LoginRequestDto)}
     */
    @Test
    void testLogin() throws AuthenticationException {
        when(jwtTokenProvider.generateToken(Mockito.<Authentication>any())).thenReturn("ABC123");
        when(authenticationManager.authenticate(Mockito.<Authentication>any()))
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        String actualLoginResult = authServiceImpl.login(new LoginRequestDto("janedoe", "iloveyou"));
        verify(jwtTokenProvider).generateToken(Mockito.<Authentication>any());
        verify(authenticationManager).authenticate(Mockito.<Authentication>any());
        assertEquals("ABC123", actualLoginResult);
    }

    /**
     * Method under test: {@link AuthServiceImpl#login(LoginRequestDto)}
     */
    @Test
    void testLogin2() throws AuthenticationException {
        when(authenticationManager.authenticate(Mockito.<Authentication>any()))
                .thenThrow(new AppException(HttpStatus.CONTINUE, "An error occurred"));
        assertThrows(AppException.class, () -> authServiceImpl.login(new LoginRequestDto("janedoe", "iloveyou")));
        verify(authenticationManager).authenticate(Mockito.<Authentication>any());
    }

    /**
     * Method under test: {@link AuthServiceImpl#register(RegisterDtoRequest)}
     */
    @Test
    void testRegister() {
        when(userRepository.existsByUsername(Mockito.<String>any())).thenReturn(true);
        assertThrows(AppException.class,
                () -> authServiceImpl.register(new RegisterDtoRequest("janedoe", "iloveyou", new RoleDtoRequest("Name"))));
        verify(userRepository).existsByUsername(Mockito.<String>any());
    }

    /**
     * Method under test: {@link AuthServiceImpl#register(RegisterDtoRequest)}
     */
    @Test
    void testRegister2() throws AuthenticationException {
        Role role = new Role();
        role.setId(1L);
        role.setName("Name");

        User user = new User();
        user.setId(1L);
        user.setPassword("iloveyou");
        user.setRole(role);
        user.setUsername("janedoe");
        when(userRepository.save(Mockito.<User>any())).thenReturn(user);
        when(userRepository.existsByUsername(Mockito.<String>any())).thenReturn(false);
        when(jwtTokenProvider.generateToken(Mockito.<Authentication>any())).thenReturn("ABC123");

        Role role2 = new Role();
        role2.setId(1L);
        role2.setName("Name");
        Optional<Role> ofResult = Optional.of(role2);
        when(roleRepository.findByName(Mockito.<String>any())).thenReturn(ofResult);
        when(authenticationManager.authenticate(Mockito.<Authentication>any()))
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        when(passwordEncoder.encode(Mockito.<CharSequence>any())).thenReturn("secret");
        String actualRegisterResult = authServiceImpl
                .register(new RegisterDtoRequest("janedoe", "iloveyou", new RoleDtoRequest("Name")));
        verify(jwtTokenProvider).generateToken(Mockito.<Authentication>any());
        verify(roleRepository).findByName(Mockito.<String>any());
        verify(userRepository).existsByUsername(Mockito.<String>any());
        verify(userRepository).save(Mockito.<User>any());
        verify(authenticationManager).authenticate(Mockito.<Authentication>any());
        verify(passwordEncoder).encode(Mockito.<CharSequence>any());
        assertEquals("ABC123", actualRegisterResult);
    }

    /**
     * Method under test: {@link AuthServiceImpl#register(RegisterDtoRequest)}
     */
    @Test
    void testRegister3() {
        when(userRepository.existsByUsername(Mockito.<String>any())).thenReturn(false);
        when(passwordEncoder.encode(Mockito.<CharSequence>any()))
                .thenThrow(new AppException(HttpStatus.CONTINUE, "An error occurred"));
        assertThrows(AppException.class,
                () -> authServiceImpl.register(new RegisterDtoRequest("janedoe", "iloveyou", new RoleDtoRequest("Name"))));
        verify(userRepository).existsByUsername(Mockito.<String>any());
        verify(passwordEncoder).encode(Mockito.<CharSequence>any());
    }

    /**
     * Method under test: {@link AuthServiceImpl#register(RegisterDtoRequest)}
     */
    @Test
    void testRegister4() {
        when(userRepository.existsByUsername(Mockito.<String>any())).thenReturn(false);
        Optional<Role> emptyResult = Optional.empty();
        when(roleRepository.findByName(Mockito.<String>any())).thenReturn(emptyResult);
        when(passwordEncoder.encode(Mockito.<CharSequence>any())).thenReturn("secret");
        assertThrows(ResourceNotFoundException.class,
                () -> authServiceImpl.register(new RegisterDtoRequest("janedoe", "iloveyou", new RoleDtoRequest("Name"))));
        verify(roleRepository).findByName(Mockito.<String>any());
        verify(userRepository).existsByUsername(Mockito.<String>any());
        verify(passwordEncoder).encode(Mockito.<CharSequence>any());
    }

    /**
     * Method under test:
     * {@link AuthServiceImpl#resetPassword(ResetPasswordDtoRequest)}
     */
    @Test
    void testResetPassword() {
        Role role = new Role();
        role.setId(1L);
        role.setName("Name");

        User user = new User();
        user.setId(1L);
        user.setPassword("iloveyou");
        user.setRole(role);
        user.setUsername("janedoe");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(ofResult);
        when(iAuthenticationFacade.getAuthentication())
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        when(passwordEncoder.matches(Mockito.<CharSequence>any(), Mockito.<String>any())).thenReturn(true);
        assertThrows(AppException.class,
                () -> authServiceImpl.resetPassword(new ResetPasswordDtoRequest("iloveyou", "iloveyou", "iloveyou")));
        verify(iAuthenticationFacade).getAuthentication();
        verify(userRepository).findByUsername(Mockito.<String>any());
        verify(passwordEncoder).matches(Mockito.<CharSequence>any(), Mockito.<String>any());
    }

    /**
     * Method under test:
     * {@link AuthServiceImpl#resetPassword(ResetPasswordDtoRequest)}
     */
    @Test
    void testResetPassword2() {
        Role role = new Role();
        role.setId(1L);
        role.setName("Name");

        User user = new User();
        user.setId(1L);
        user.setPassword("iloveyou");
        user.setRole(role);
        user.setUsername("janedoe");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(ofResult);
        when(iAuthenticationFacade.getAuthentication())
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        when(passwordEncoder.matches(Mockito.<CharSequence>any(), Mockito.<String>any()))
                .thenThrow(new ResourceNotFoundException("iloveyou", "iloveyou", "42"));
        assertThrows(ResourceNotFoundException.class,
                () -> authServiceImpl.resetPassword(new ResetPasswordDtoRequest("iloveyou", "iloveyou", "iloveyou")));
        verify(iAuthenticationFacade).getAuthentication();
        verify(userRepository).findByUsername(Mockito.<String>any());
        verify(passwordEncoder).matches(Mockito.<CharSequence>any(), Mockito.<String>any());
    }

    /**
     * Method under test:
     * {@link AuthServiceImpl#resetPassword(ResetPasswordDtoRequest)}
     */
    @Test
    void testResetPassword3() {
        Optional<User> emptyResult = Optional.empty();
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(emptyResult);
        when(iAuthenticationFacade.getAuthentication())
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        assertThrows(ResourceNotFoundException.class,
                () -> authServiceImpl.resetPassword(new ResetPasswordDtoRequest("iloveyou", "iloveyou", "iloveyou")));
        verify(iAuthenticationFacade).getAuthentication();
        verify(userRepository).findByUsername(Mockito.<String>any());
    }

    /**
     * Method under test:
     * {@link AuthServiceImpl#resetPassword(ResetPasswordDtoRequest)}
     */
    @Test
    void testResetPassword4() {
        Role role = new Role();
        role.setId(1L);
        role.setName("Name");

        User user = new User();
        user.setId(1L);
        user.setPassword("iloveyou");
        user.setRole(role);
        user.setUsername("janedoe");
        Optional<User> ofResult = Optional.of(user);

        Role role2 = new Role();
        role2.setId(1L);
        role2.setName("Name");

        User user2 = new User();
        user2.setId(1L);
        user2.setPassword("iloveyou");
        user2.setRole(role2);
        user2.setUsername("janedoe");
        when(userRepository.save(Mockito.<User>any())).thenReturn(user2);
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(ofResult);
        when(iAuthenticationFacade.getAuthentication())
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        when(passwordEncoder.matches(Mockito.<CharSequence>any(), Mockito.<String>any())).thenReturn(false);
        when(passwordEncoder.encode(Mockito.<CharSequence>any())).thenReturn("secret");
        authServiceImpl.resetPassword(new ResetPasswordDtoRequest("iloveyou", "iloveyou", "iloveyou"));
        verify(iAuthenticationFacade).getAuthentication();
        verify(userRepository).findByUsername(Mockito.<String>any());
        verify(userRepository).save(Mockito.<User>any());
        verify(passwordEncoder).encode(Mockito.<CharSequence>any());
        verify(passwordEncoder).matches(Mockito.<CharSequence>any(), Mockito.<String>any());
    }

    /**
     * Method under test:
     * {@link AuthServiceImpl#resetPassword(ResetPasswordDtoRequest)}
     */
    @Test
    void testResetPassword5() {
        Role role = new Role();
        role.setId(1L);
        role.setName("Name");

        User user = new User();
        user.setId(1L);
        user.setPassword("iloveyou");
        user.setRole(role);
        user.setUsername("janedoe");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(ofResult);
        when(iAuthenticationFacade.getAuthentication())
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        assertThrows(AppException.class, () -> authServiceImpl.resetPassword(new ResetPasswordDtoRequest("iloveyou",
                "The new password must be different from the current password", "iloveyou")));
        verify(iAuthenticationFacade).getAuthentication();
        verify(userRepository).findByUsername(Mockito.<String>any());
    }
}
