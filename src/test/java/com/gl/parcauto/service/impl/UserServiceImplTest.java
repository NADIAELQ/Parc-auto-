package com.gl.parcauto.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.gl.parcauto.auth.AuthenticationFacade;
import com.gl.parcauto.dto.request.RoleDtoRequest;
import com.gl.parcauto.dto.request.UserDtoRequest;
import com.gl.parcauto.dto.request.UserPasswordDtoRequest;
import com.gl.parcauto.dto.request.UserUpdateDtoRequest;
import com.gl.parcauto.dto.response.RoleDtoResponse;
import com.gl.parcauto.dto.response.UserDtoResponse;
import com.gl.parcauto.entity.Role;
import com.gl.parcauto.entity.User;
import com.gl.parcauto.exception.AppException;
import com.gl.parcauto.exception.ResourceNotFoundException;
import com.gl.parcauto.repository.RoleRepository;
import com.gl.parcauto.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserServiceImpl.class})
@ExtendWith(SpringExtension.class)
class UserServiceImplTest {
    @MockBean
    private AuthenticationFacade authenticationFacade;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;

    /**
     * Method under test: {@link UserServiceImpl#createUser(UserDtoRequest)}
     */
    @Test
    void testCreateUser() {
        when(userRepository.existsByUsername(Mockito.<String>any())).thenReturn(true);
        assertThrows(AppException.class,
                () -> userServiceImpl.createUser(new UserDtoRequest("janedoe", "iloveyou", new RoleDtoRequest("Name"))));
        verify(userRepository).existsByUsername(Mockito.<String>any());
    }

    /**
     * Method under test: {@link UserServiceImpl#createUser(UserDtoRequest)}
     */
    @Test
    void testCreateUser2() {
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

        Role role2 = new Role();
        role2.setId(1L);
        role2.setName("Name");
        Optional<Role> ofResult = Optional.of(role2);
        when(roleRepository.findByName(Mockito.<String>any())).thenReturn(ofResult);
        when(passwordEncoder.encode(Mockito.<CharSequence>any())).thenReturn("secret");
        UserDtoResponse actualCreateUserResult = userServiceImpl
                .createUser(new UserDtoRequest("janedoe", "iloveyou", new RoleDtoRequest("Name")));
        verify(roleRepository).findByName(Mockito.<String>any());
        verify(userRepository).existsByUsername(Mockito.<String>any());
        verify(userRepository).save(Mockito.<User>any());
        verify(passwordEncoder).encode(Mockito.<CharSequence>any());
        RoleDtoResponse roleResult = actualCreateUserResult.role();
        assertEquals("Name", roleResult.name());
        assertEquals("janedoe", actualCreateUserResult.username());
        assertEquals(1L, roleResult.id().longValue());
        assertEquals(1L, actualCreateUserResult.id().longValue());
    }

    /**
     * Method under test: {@link UserServiceImpl#createUser(UserDtoRequest)}
     */
    @Test
    void testCreateUser3() {
        when(userRepository.existsByUsername(Mockito.<String>any())).thenReturn(false);
        when(passwordEncoder.encode(Mockito.<CharSequence>any()))
                .thenThrow(new AppException(HttpStatus.CONTINUE, "An error occurred"));
        assertThrows(AppException.class,
                () -> userServiceImpl.createUser(new UserDtoRequest("janedoe", "iloveyou", new RoleDtoRequest("Name"))));
        verify(userRepository).existsByUsername(Mockito.<String>any());
        verify(passwordEncoder).encode(Mockito.<CharSequence>any());
    }

    /**
     * Method under test: {@link UserServiceImpl#createUser(UserDtoRequest)}
     */
    @Test
    void testCreateUser4() {
        when(userRepository.existsByUsername(Mockito.<String>any())).thenReturn(false);
        Optional<Role> emptyResult = Optional.empty();
        when(roleRepository.findByName(Mockito.<String>any())).thenReturn(emptyResult);
        when(passwordEncoder.encode(Mockito.<CharSequence>any())).thenReturn("secret");
        assertThrows(ResourceNotFoundException.class,
                () -> userServiceImpl.createUser(new UserDtoRequest("janedoe", "iloveyou", new RoleDtoRequest("Name"))));
        verify(roleRepository).findByName(Mockito.<String>any());
        verify(userRepository).existsByUsername(Mockito.<String>any());
        verify(passwordEncoder).encode(Mockito.<CharSequence>any());
    }

    /**
     * Method under test: {@link UserServiceImpl#createUser(UserDtoRequest)}
     */
    @Test
    void testCreateUser5() {
        when(userRepository.existsByUsername(Mockito.<String>any())).thenReturn(false);
        Role role = mock(Role.class);
        doNothing().when(role).setId(Mockito.<Long>any());
        doNothing().when(role).setName(Mockito.<String>any());
        role.setId(1L);
        role.setName("Name");
        Optional.of(role);
        when(passwordEncoder.encode(Mockito.<CharSequence>any())).thenReturn("secret");
        assertThrows(AppException.class,
                () -> userServiceImpl.createUser(new UserDtoRequest("janedoe", "iloveyou", new RoleDtoRequest(""))));
        verify(role).setId(Mockito.<Long>any());
        verify(role).setName(Mockito.<String>any());
        verify(userRepository).existsByUsername(Mockito.<String>any());
        verify(passwordEncoder).encode(Mockito.<CharSequence>any());
    }

    /**
     * Method under test: {@link UserServiceImpl#createUser(UserDtoRequest)}
     */
    @Test
    void testCreateUser6() {
        when(userRepository.existsByUsername(Mockito.<String>any())).thenReturn(false);
        Role role = mock(Role.class);
        doNothing().when(role).setId(Mockito.<Long>any());
        doNothing().when(role).setName(Mockito.<String>any());
        role.setId(1L);
        role.setName("Name");
        Optional.of(role);
        when(passwordEncoder.encode(Mockito.<CharSequence>any())).thenReturn("secret");
        assertThrows(AppException.class, () -> userServiceImpl.createUser(new UserDtoRequest("janedoe", "iloveyou", null)));
        verify(role).setId(Mockito.<Long>any());
        verify(role).setName(Mockito.<String>any());
        verify(userRepository).existsByUsername(Mockito.<String>any());
        verify(passwordEncoder).encode(Mockito.<CharSequence>any());
    }

    /**
     * Method under test: {@link UserServiceImpl#createUser(UserDtoRequest)}
     */
    @Test
    void testCreateUser7() {
        when(userRepository.existsByUsername((String) any())).thenReturn(true);
        assertThrows(AppException.class,
                () -> userServiceImpl.createUser(new UserDtoRequest("janedoe", "iloveyou", new RoleDtoRequest("Name"))));
        verify(userRepository).existsByUsername((String) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#createUser(UserDtoRequest)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testCreateUser8() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "String.equals(Object)" because "str" is null
        //       at com.gl.parcauto.service.impl.UserServiceImpl.addRoleToUser(UserServiceImpl.java:168)
        //       at com.gl.parcauto.service.impl.UserServiceImpl.createUser(UserServiceImpl.java:55)
        //   See https://diff.blue/R013 to resolve this issue.

        when(userRepository.existsByUsername((String) any())).thenReturn(false);
        when(roleRepository.findByName((String) any())).thenReturn(Optional.of(new Role()));
        when(passwordEncoder.encode((CharSequence) any())).thenReturn("secret");
        userServiceImpl.createUser(new UserDtoRequest("janedoe", "iloveyou", new RoleDtoRequest("Name")));
    }

    /**
     * Method under test: {@link UserServiceImpl#createUser(UserDtoRequest)}
     */
    @Test
    void testCreateUser9() {
        when(userRepository.existsByUsername((String) any())).thenReturn(false);
        when(roleRepository.findByName((String) any())).thenReturn(Optional.of(new Role()));
        when(passwordEncoder.encode((CharSequence) any()))
                .thenThrow(new AppException(HttpStatus.CONTINUE, "An error occurred"));
        assertThrows(AppException.class,
                () -> userServiceImpl.createUser(new UserDtoRequest("janedoe", "iloveyou", new RoleDtoRequest("Name"))));
        verify(userRepository).existsByUsername((String) any());
        verify(passwordEncoder).encode((CharSequence) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#createUser(UserDtoRequest)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testCreateUser10() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.gl.parcauto.entity.Role.getName()" because the return value of "com.gl.parcauto.entity.User.getRole()" is null
        //       at com.gl.parcauto.service.impl.UserServiceImpl.isAdmin(UserServiceImpl.java:180)
        //       at com.gl.parcauto.service.impl.UserServiceImpl.addRoleToUser(UserServiceImpl.java:168)
        //       at com.gl.parcauto.service.impl.UserServiceImpl.createUser(UserServiceImpl.java:55)
        //   See https://diff.blue/R013 to resolve this issue.

        when(userRepository.findByUsername((String) any())).thenReturn(Optional.of(new User()));
        when(userRepository.existsByUsername((String) any())).thenReturn(false);
        when(roleRepository.findByName((String) any())).thenReturn(Optional.of(new Role(1L, "ROLE_ADMIN")));
        when(authenticationFacade.getAuthentication())
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        when(passwordEncoder.encode((CharSequence) any())).thenReturn("secret");
        userServiceImpl.createUser(new UserDtoRequest("janedoe", "iloveyou", new RoleDtoRequest("Name")));
    }

    /**
     * Method under test: {@link UserServiceImpl#createUser(UserDtoRequest)}
     */
    @Test
    void testCreateUser11() {
        when(userRepository.findByUsername((String) any()))
                .thenThrow(new AppException(HttpStatus.CONTINUE, "An error occurred"));
        when(userRepository.existsByUsername((String) any())).thenReturn(false);
        when(roleRepository.findByName((String) any())).thenReturn(Optional.of(new Role(1L, "ROLE_ADMIN")));
        when(authenticationFacade.getAuthentication())
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        when(passwordEncoder.encode((CharSequence) any())).thenReturn("secret");
        assertThrows(AppException.class,
                () -> userServiceImpl.createUser(new UserDtoRequest("janedoe", "iloveyou", new RoleDtoRequest("Name"))));
        verify(userRepository).existsByUsername((String) any());
        verify(userRepository).findByUsername((String) any());
        verify(roleRepository).findByName((String) any());
        verify(authenticationFacade).getAuthentication();
        verify(passwordEncoder).encode((CharSequence) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#createUser(UserDtoRequest)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testCreateUser12() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "String.equals(Object)" because "str" is null
        //       at com.gl.parcauto.service.impl.UserServiceImpl.isAdmin(UserServiceImpl.java:180)
        //       at com.gl.parcauto.service.impl.UserServiceImpl.addRoleToUser(UserServiceImpl.java:168)
        //       at com.gl.parcauto.service.impl.UserServiceImpl.createUser(UserServiceImpl.java:55)
        //   See https://diff.blue/R013 to resolve this issue.

        User user = new User();
        user.setRole(new Role());
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findByUsername((String) any())).thenReturn(ofResult);
        when(userRepository.existsByUsername((String) any())).thenReturn(false);
        when(roleRepository.findByName((String) any())).thenReturn(Optional.of(new Role(1L, "ROLE_ADMIN")));
        when(authenticationFacade.getAuthentication())
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        when(passwordEncoder.encode((CharSequence) any())).thenReturn("secret");
        userServiceImpl.createUser(new UserDtoRequest("janedoe", "iloveyou", new RoleDtoRequest("Name")));
    }

    /**
     * Method under test: {@link UserServiceImpl#createUser(UserDtoRequest)}
     */
    @Test
    void testCreateUser13() {
        User user = new User();
        user.setRole(new Role(1L, "ROLE_ADMIN"));
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.save((User) any())).thenReturn(new User());
        when(userRepository.findByUsername((String) any())).thenReturn(ofResult);
        when(userRepository.existsByUsername((String) any())).thenReturn(false);
        when(roleRepository.findByName((String) any())).thenReturn(Optional.of(new Role(1L, "ROLE_ADMIN")));
        when(authenticationFacade.getAuthentication())
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        when(passwordEncoder.encode((CharSequence) any())).thenReturn("secret");
        UserDtoResponse actualCreateUserResult = userServiceImpl
                .createUser(new UserDtoRequest("janedoe", "iloveyou", new RoleDtoRequest("Name")));
        assertNull(actualCreateUserResult.id());
        assertNull(actualCreateUserResult.username());
        assertNull(actualCreateUserResult.role());
        verify(userRepository).existsByUsername((String) any());
        verify(userRepository).save((User) any());
        verify(userRepository).findByUsername((String) any());
        verify(roleRepository).findByName((String) any());
        verify(authenticationFacade).getAuthentication();
        verify(passwordEncoder).encode((CharSequence) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#createUser(UserDtoRequest)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testCreateUser14() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.gl.parcauto.exception.ResourceNotFoundException: Resource Name not found with Field Name : '42'
        //       at com.gl.parcauto.mapper.UserMapperImpl.userToUserDtoResponse(UserMapperImpl.java:59)
        //       at com.gl.parcauto.service.impl.UserServiceImpl.createUser(UserServiceImpl.java:60)
        //   See https://diff.blue/R013 to resolve this issue.

        User user = new User();
        user.setRole(new Role(1L, "ROLE_ADMIN"));
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.save((User) any())).thenReturn(mock(User.class));
        when(userRepository.findByUsername((String) any())).thenReturn(ofResult);
        when(userRepository.existsByUsername((String) any())).thenReturn(false);
        new ResourceNotFoundException("Resource Name", "Field Name", "42");

        new ResourceNotFoundException("Resource Name", "Field Name", "42");

        new ResourceNotFoundException("Resource Name", "Field Name", "42");

        when(roleRepository.findByName((String) any())).thenReturn(Optional.of(new Role(1L, "ROLE_ADMIN")));
        when(authenticationFacade.getAuthentication())
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        when(passwordEncoder.encode((CharSequence) any())).thenReturn("secret");
        userServiceImpl.createUser(new UserDtoRequest("janedoe", "iloveyou", new RoleDtoRequest("Name")));
    }

    /**
     * Method under test: {@link UserServiceImpl#createUser(UserDtoRequest)}
     */
    @Test
    void testCreateUser15() {
        User user = new User();
        user.setRole(new Role(1L, "Name"));
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.save((User) any())).thenReturn(mock(User.class));
        when(userRepository.findByUsername((String) any())).thenReturn(ofResult);
        when(userRepository.existsByUsername((String) any())).thenReturn(false);
        new ResourceNotFoundException("Resource Name", "Field Name", "42");

        new ResourceNotFoundException("Resource Name", "Field Name", "42");

        new ResourceNotFoundException("Resource Name", "Field Name", "42");

        when(roleRepository.findByName((String) any())).thenReturn(Optional.of(new Role(1L, "ROLE_ADMIN")));
        when(authenticationFacade.getAuthentication())
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        when(passwordEncoder.encode((CharSequence) any())).thenReturn("secret");
        assertThrows(AppException.class,
                () -> userServiceImpl.createUser(new UserDtoRequest("janedoe", "iloveyou", new RoleDtoRequest("Name"))));
        verify(userRepository).existsByUsername((String) any());
        verify(userRepository).findByUsername((String) any());
        verify(roleRepository).findByName((String) any());
        verify(authenticationFacade).getAuthentication();
        verify(passwordEncoder).encode((CharSequence) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#createUser(UserDtoRequest)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testCreateUser16() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.util.Optional.orElseThrow(java.util.function.Supplier)" because the return value of "com.gl.parcauto.repository.UserRepository.findByUsername(String)" is null
        //       at com.gl.parcauto.service.impl.UserServiceImpl.isAdmin(UserServiceImpl.java:177)
        //       at com.gl.parcauto.service.impl.UserServiceImpl.addRoleToUser(UserServiceImpl.java:168)
        //       at com.gl.parcauto.service.impl.UserServiceImpl.createUser(UserServiceImpl.java:55)
        //   See https://diff.blue/R013 to resolve this issue.

        when(userRepository.save((User) any())).thenReturn(mock(User.class));
        when(userRepository.findByUsername((String) any())).thenReturn(null);
        when(userRepository.existsByUsername((String) any())).thenReturn(false);
        new ResourceNotFoundException("Resource Name", "Field Name", "42");

        new ResourceNotFoundException("Resource Name", "Field Name", "42");

        new ResourceNotFoundException("Resource Name", "Field Name", "42");

        when(roleRepository.findByName((String) any())).thenReturn(Optional.of(new Role(1L, "ROLE_ADMIN")));
        when(authenticationFacade.getAuthentication())
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        when(passwordEncoder.encode((CharSequence) any())).thenReturn("secret");
        userServiceImpl.createUser(new UserDtoRequest("janedoe", "iloveyou", new RoleDtoRequest("Name")));
    }

    /**
     * Method under test: {@link UserServiceImpl#createUser(UserDtoRequest)}
     */
    @Test
    void testCreateUser17() {
        when(userRepository.save((User) any())).thenReturn(mock(User.class));
        when(userRepository.findByUsername((String) any())).thenReturn(Optional.empty());
        when(userRepository.existsByUsername((String) any())).thenReturn(false);
        new ResourceNotFoundException("Resource Name", "Field Name", "42");

        new ResourceNotFoundException("Resource Name", "Field Name", "42");

        new ResourceNotFoundException("Resource Name", "Field Name", "42");

        when(roleRepository.findByName((String) any())).thenReturn(Optional.of(new Role(1L, "ROLE_ADMIN")));
        when(authenticationFacade.getAuthentication())
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        when(passwordEncoder.encode((CharSequence) any())).thenReturn("secret");
        assertThrows(ResourceNotFoundException.class,
                () -> userServiceImpl.createUser(new UserDtoRequest("janedoe", "iloveyou", new RoleDtoRequest("Name"))));
        verify(userRepository).existsByUsername((String) any());
        verify(userRepository).findByUsername((String) any());
        verify(roleRepository).findByName((String) any());
        verify(authenticationFacade).getAuthentication();
        verify(passwordEncoder).encode((CharSequence) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#createUser(UserDtoRequest)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testCreateUser18() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.lang.Boolean.booleanValue()" because the return value of "com.gl.parcauto.repository.UserRepository.existsByUsername(String)" is null
        //       at com.gl.parcauto.service.impl.UserServiceImpl.createUser(UserServiceImpl.java:40)
        //   See https://diff.blue/R013 to resolve this issue.

        User user = new User();
        user.setRole(new Role(1L, "ROLE_ADMIN"));
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.save((User) any())).thenReturn(mock(User.class));
        when(userRepository.findByUsername((String) any())).thenReturn(ofResult);
        when(userRepository.existsByUsername((String) any())).thenReturn(null);
        new ResourceNotFoundException("Resource Name", "Field Name", "42");

        new ResourceNotFoundException("Resource Name", "Field Name", "42");

        new ResourceNotFoundException("Resource Name", "Field Name", "42");

        when(roleRepository.findByName((String) any())).thenReturn(Optional.of(new Role(1L, "ROLE_ADMIN")));
        when(authenticationFacade.getAuthentication())
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        when(passwordEncoder.encode((CharSequence) any())).thenReturn("secret");
        userServiceImpl.createUser(new UserDtoRequest("janedoe", "iloveyou", new RoleDtoRequest("Name")));
    }

    /**
     * Method under test: {@link UserServiceImpl#getUserById(Long)}
     */
    @Test
    void testGetUserById() {
        Role role = new Role();
        role.setId(1L);
        role.setName("Name");

        User user = new User();
        user.setId(1L);
        user.setPassword("iloveyou");
        user.setRole(role);
        user.setUsername("janedoe");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        UserDtoResponse actualUserById = userServiceImpl.getUserById(1L);
        verify(userRepository).findById(Mockito.<Long>any());
        RoleDtoResponse roleResult = actualUserById.role();
        assertEquals("Name", roleResult.name());
        assertEquals("janedoe", actualUserById.username());
        assertEquals(1L, roleResult.id().longValue());
        assertEquals(1L, actualUserById.id().longValue());
    }

    /**
     * Method under test: {@link UserServiceImpl#getUserById(Long)}
     */
    @Test
    void testGetUserById2() {
        Optional<User> emptyResult = Optional.empty();
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);
        assertThrows(ResourceNotFoundException.class, () -> userServiceImpl.getUserById(1L));
        verify(userRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link UserServiceImpl#getUserById(Long)}
     */
    @Test
    void testGetUserById3() {
        when(userRepository.findById(Mockito.<Long>any()))
                .thenThrow(new AppException(HttpStatus.CONTINUE, "An error occurred"));
        assertThrows(AppException.class, () -> userServiceImpl.getUserById(1L));
        verify(userRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link UserServiceImpl#getUserById(Long)}
     */
    @Test
    void testGetUserById4() {
        when(userRepository.findById((Long) any())).thenReturn(Optional.of(new User()));
        UserDtoResponse actualUserById = userServiceImpl.getUserById(1L);
        assertNull(actualUserById.id());
        assertNull(actualUserById.username());
        assertNull(actualUserById.role());
        verify(userRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#getUserById(Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetUserById5() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.gl.parcauto.exception.ResourceNotFoundException: Resource Name not found with Field Name : '42'
        //       at com.gl.parcauto.mapper.UserMapperImpl.userToUserDtoResponse(UserMapperImpl.java:59)
        //       at com.gl.parcauto.service.impl.UserServiceImpl.getUserById(UserServiceImpl.java:69)
        //   See https://diff.blue/R013 to resolve this issue.

        User user = mock(User.class);
        when(user.getRole()).thenThrow(new ResourceNotFoundException("Resource Name", "Field Name", "42"));
        when(user.getId()).thenThrow(new ResourceNotFoundException("Resource Name", "Field Name", "42"));
        when(user.getUsername()).thenThrow(new ResourceNotFoundException("Resource Name", "Field Name", "42"));
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById((Long) any())).thenReturn(ofResult);
        userServiceImpl.getUserById(1L);
    }

    /**
     * Method under test: {@link UserServiceImpl#getUserById(Long)}
     */
    @Test
    void testGetUserById6() {
        when(userRepository.findById((Long) any())).thenReturn(Optional.empty());
        new ResourceNotFoundException("Resource Name", "Field Name", "42");

        new ResourceNotFoundException("Resource Name", "Field Name", "42");

        new ResourceNotFoundException("Resource Name", "Field Name", "42");

        assertThrows(ResourceNotFoundException.class, () -> userServiceImpl.getUserById(1L));
        verify(userRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#getUserByUsername(String)}
     */
    @Test
    void testGetUserByUsername() {
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
        UserDtoResponse actualUserByUsername = userServiceImpl.getUserByUsername("janedoe");
        verify(userRepository).findByUsername(Mockito.<String>any());
        RoleDtoResponse roleResult = actualUserByUsername.role();
        assertEquals("Name", roleResult.name());
        assertEquals("janedoe", actualUserByUsername.username());
        assertEquals(1L, roleResult.id().longValue());
        assertEquals(1L, actualUserByUsername.id().longValue());
    }

    /**
     * Method under test: {@link UserServiceImpl#getUserByUsername(String)}
     */
    @Test
    void testGetUserByUsername2() {
        Optional<User> emptyResult = Optional.empty();
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(emptyResult);
        assertThrows(ResourceNotFoundException.class, () -> userServiceImpl.getUserByUsername("janedoe"));
        verify(userRepository).findByUsername(Mockito.<String>any());
    }

    /**
     * Method under test: {@link UserServiceImpl#getUserByUsername(String)}
     */
    @Test
    void testGetUserByUsername3() {
        when(userRepository.findByUsername(Mockito.<String>any()))
                .thenThrow(new AppException(HttpStatus.CONTINUE, "An error occurred"));
        assertThrows(AppException.class, () -> userServiceImpl.getUserByUsername("janedoe"));
        verify(userRepository).findByUsername(Mockito.<String>any());
    }

    /**
     * Method under test: {@link UserServiceImpl#getUserByUsername(String)}
     */
    @Test
    void testGetUserByUsername4() {
        when(userRepository.findByUsername((String) any())).thenReturn(Optional.of(new User()));
        UserDtoResponse actualUserByUsername = userServiceImpl.getUserByUsername("janedoe");
        assertNull(actualUserByUsername.id());
        assertNull(actualUserByUsername.username());
        assertNull(actualUserByUsername.role());
        verify(userRepository).findByUsername((String) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#getUserByUsername(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetUserByUsername5() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.gl.parcauto.exception.ResourceNotFoundException: Resource Name not found with Field Name : '42'
        //       at com.gl.parcauto.mapper.UserMapperImpl.userToUserDtoResponse(UserMapperImpl.java:59)
        //       at com.gl.parcauto.service.impl.UserServiceImpl.getUserByUsername(UserServiceImpl.java:78)
        //   See https://diff.blue/R013 to resolve this issue.

        User user = mock(User.class);
        when(user.getRole()).thenThrow(new ResourceNotFoundException("Resource Name", "Field Name", "42"));
        when(user.getId()).thenThrow(new ResourceNotFoundException("Resource Name", "Field Name", "42"));
        when(user.getUsername()).thenThrow(new ResourceNotFoundException("Resource Name", "Field Name", "42"));
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findByUsername((String) any())).thenReturn(ofResult);
        userServiceImpl.getUserByUsername("janedoe");
    }

    /**
     * Method under test: {@link UserServiceImpl#getUserByUsername(String)}
     */
    @Test
    void testGetUserByUsername6() {
        when(userRepository.findByUsername((String) any())).thenReturn(Optional.empty());
        new ResourceNotFoundException("Resource Name", "Field Name", "42");

        new ResourceNotFoundException("Resource Name", "Field Name", "42");

        new ResourceNotFoundException("Resource Name", "Field Name", "42");

        assertThrows(ResourceNotFoundException.class, () -> userServiceImpl.getUserByUsername("janedoe"));
        verify(userRepository).findByUsername((String) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#getAllUsers()}
     */
    @Test
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(new ArrayList<>());
        List<UserDtoResponse> actualAllUsers = userServiceImpl.getAllUsers();
        verify(userRepository).findAll();
        assertTrue(actualAllUsers.isEmpty());
    }

    /**
     * Method under test: {@link UserServiceImpl#getAllUsers()}
     */
    @Test
    void testGetAllUsers2() {
        when(userRepository.findAll()).thenThrow(new AppException(HttpStatus.CONTINUE, "An error occurred"));
        assertThrows(AppException.class, () -> userServiceImpl.getAllUsers());
        verify(userRepository).findAll();
    }

    /**
     * Method under test: {@link UserServiceImpl#getAllUsers()}
     */
    @Test
    void testGetAllUsers3() {
        when(userRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(userServiceImpl.getAllUsers().isEmpty());
        verify(userRepository).findAll();
    }

    /**
     * Method under test:
     * {@link UserServiceImpl#updateUser(UserUpdateDtoRequest, Long)}
     */
    @Test
    void testUpdateUser() {
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
        Optional<User> ofResult2 = Optional.of(user2);
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(ofResult2);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(authenticationFacade.getAuthentication())
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        assertThrows(AppException.class, () -> userServiceImpl.updateUser(new UserUpdateDtoRequest("janedoe"), 1L));
        verify(authenticationFacade, atLeast(1)).getAuthentication();
        verify(userRepository).findByUsername(Mockito.<String>any());
        verify(userRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test:
     * {@link UserServiceImpl#updateUser(UserUpdateDtoRequest, Long)}
     */
    @Test
    void testUpdateUser2() {
        Role role = new Role();
        role.setId(1L);
        role.setName("Name");

        User user = new User();
        user.setId(1L);
        user.setPassword("iloveyou");
        user.setRole(role);
        user.setUsername("janedoe");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findByUsername(Mockito.<String>any()))
                .thenThrow(new AppException(HttpStatus.CONTINUE, "An error occurred"));
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(authenticationFacade.getAuthentication())
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        assertThrows(AppException.class, () -> userServiceImpl.updateUser(new UserUpdateDtoRequest("janedoe"), 1L));
        verify(authenticationFacade).getAuthentication();
        verify(userRepository).findByUsername(Mockito.<String>any());
        verify(userRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test:
     * {@link UserServiceImpl#updateUser(UserUpdateDtoRequest, Long)}
     */
    @Test
    void testUpdateUser3() {
        Role role = new Role();
        role.setId(1L);
        role.setName("Name");

        User user = new User();
        user.setId(1L);
        user.setPassword("iloveyou");
        user.setRole(role);
        user.setUsername("janedoe");
        Optional<User> ofResult = Optional.of(user);
        Optional<User> emptyResult = Optional.empty();
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(emptyResult);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(authenticationFacade.getAuthentication())
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        assertThrows(ResourceNotFoundException.class,
                () -> userServiceImpl.updateUser(new UserUpdateDtoRequest("janedoe"), 1L));
        verify(authenticationFacade).getAuthentication();
        verify(userRepository).findByUsername(Mockito.<String>any());
        verify(userRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test:
     * {@link UserServiceImpl#updateUser(UserUpdateDtoRequest, Long)}
     */
    @Test
    void testUpdateUser4() {
        Optional<User> emptyResult = Optional.empty();
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);
        Role role = mock(Role.class);
        doNothing().when(role).setId(Mockito.<Long>any());
        doNothing().when(role).setName(Mockito.<String>any());
        role.setId(1L);
        role.setName("Name");

        Role role2 = new Role();
        role2.setId(1L);
        role2.setName("Name");
        User user = mock(User.class);
        doNothing().when(user).setId(Mockito.<Long>any());
        doNothing().when(user).setPassword(Mockito.<String>any());
        doNothing().when(user).setRole(Mockito.<Role>any());
        doNothing().when(user).setUsername(Mockito.<String>any());
        user.setId(1L);
        user.setPassword("iloveyou");
        user.setRole(role2);
        user.setUsername("janedoe");
        Optional.of(user);
        assertThrows(ResourceNotFoundException.class,
                () -> userServiceImpl.updateUser(new UserUpdateDtoRequest("janedoe"), 1L));
        verify(role).setId(Mockito.<Long>any());
        verify(role).setName(Mockito.<String>any());
        verify(user).setId(Mockito.<Long>any());
        verify(user).setPassword(Mockito.<String>any());
        verify(user).setRole(Mockito.<Role>any());
        verify(user).setUsername(Mockito.<String>any());
        verify(userRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link UserServiceImpl#updateUser(UserUpdateDtoRequest, Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateUser5() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.gl.parcauto.entity.Role.getName()" because the return value of "com.gl.parcauto.entity.User.getRole()" is null
        //       at com.gl.parcauto.service.impl.UserServiceImpl.isAdmin(UserServiceImpl.java:180)
        //       at com.gl.parcauto.service.impl.UserServiceImpl.updateUser(UserServiceImpl.java:95)
        //   See https://diff.blue/R013 to resolve this issue.

        when(userRepository.findByUsername((String) any())).thenReturn(Optional.of(new User()));
        when(userRepository.findById((Long) any())).thenReturn(Optional.of(new User()));
        when(authenticationFacade.getAuthentication())
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        userServiceImpl.updateUser(new UserUpdateDtoRequest("janedoe"), 1L);
    }

    /**
     * Method under test: {@link UserServiceImpl#updateUser(UserUpdateDtoRequest, Long)}
     */
    @Test
    void testUpdateUser6() {
        when(userRepository.findByUsername((String) any()))
                .thenThrow(new AppException(HttpStatus.CONTINUE, "An error occurred"));
        when(userRepository.findById((Long) any())).thenReturn(Optional.of(new User()));
        when(authenticationFacade.getAuthentication())
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        assertThrows(AppException.class, () -> userServiceImpl.updateUser(new UserUpdateDtoRequest("janedoe"), 1L));
        verify(userRepository).findByUsername((String) any());
        verify(userRepository).findById((Long) any());
        verify(authenticationFacade).getAuthentication();
    }

    /**
     * Method under test: {@link UserServiceImpl#updateUser(UserUpdateDtoRequest, Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateUser7() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "String.equals(Object)" because "str" is null
        //       at com.gl.parcauto.service.impl.UserServiceImpl.isAdmin(UserServiceImpl.java:180)
        //       at com.gl.parcauto.service.impl.UserServiceImpl.updateUser(UserServiceImpl.java:95)
        //   See https://diff.blue/R013 to resolve this issue.

        User user = new User();
        user.setRole(new Role());
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findByUsername((String) any())).thenReturn(ofResult);
        when(userRepository.findById((Long) any())).thenReturn(Optional.of(new User()));
        when(authenticationFacade.getAuthentication())
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        userServiceImpl.updateUser(new UserUpdateDtoRequest("janedoe"), 1L);
    }

    /**
     * Method under test: {@link UserServiceImpl#updateUser(UserUpdateDtoRequest, Long)}
     */
    @Test
    void testUpdateUser8() {
        User user = new User();
        user.setRole(new Role(1L, "ROLE_ADMIN"));
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.save((User) any())).thenReturn(new User());
        when(userRepository.findByUsername((String) any())).thenReturn(ofResult);
        when(userRepository.findById((Long) any())).thenReturn(Optional.of(new User()));
        when(authenticationFacade.getAuthentication())
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        UserDtoResponse actualUpdateUserResult = userServiceImpl.updateUser(new UserUpdateDtoRequest("janedoe"), 1L);
        assertNull(actualUpdateUserResult.id());
        assertNull(actualUpdateUserResult.username());
        assertNull(actualUpdateUserResult.role());
        verify(userRepository).save((User) any());
        verify(userRepository).findByUsername((String) any());
        verify(userRepository).findById((Long) any());
        verify(authenticationFacade).getAuthentication();
    }

    /**
     * Method under test: {@link UserServiceImpl#updateUser(UserUpdateDtoRequest, Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateUser9() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.gl.parcauto.exception.ResourceNotFoundException: Resource Name not found with Field Name : '42'
        //       at com.gl.parcauto.mapper.UserMapperImpl.userToUserDtoResponse(UserMapperImpl.java:59)
        //       at com.gl.parcauto.service.impl.UserServiceImpl.updateUser(UserServiceImpl.java:105)
        //   See https://diff.blue/R013 to resolve this issue.

        User user = new User();
        user.setRole(new Role(1L, "ROLE_ADMIN"));
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.save((User) any())).thenReturn(mock(User.class));
        when(userRepository.findByUsername((String) any())).thenReturn(ofResult);
        when(userRepository.findById((Long) any())).thenReturn(Optional.of(new User()));
        new ResourceNotFoundException("Resource Name", "Field Name", "42");

        new ResourceNotFoundException("Resource Name", "Field Name", "42");

        new ResourceNotFoundException("Resource Name", "Field Name", "42");

        when(authenticationFacade.getAuthentication())
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        userServiceImpl.updateUser(new UserUpdateDtoRequest("janedoe"), 1L);
    }

    /**
     * Method under test: {@link UserServiceImpl#updateUser(UserUpdateDtoRequest, Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateUser10() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException
        //   See https://diff.blue/R013 to resolve this issue.

        User user = new User();
        user.setRole(new Role(1L, "Name"));
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.save((User) any())).thenReturn(mock(User.class));
        when(userRepository.findByUsername((String) any())).thenReturn(ofResult);
        when(userRepository.findById((Long) any())).thenReturn(Optional.of(new User()));
        new ResourceNotFoundException("Resource Name", "Field Name", "42");

        new ResourceNotFoundException("Resource Name", "Field Name", "42");

        new ResourceNotFoundException("Resource Name", "Field Name", "42");

        when(authenticationFacade.getAuthentication())
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        userServiceImpl.updateUser(new UserUpdateDtoRequest("janedoe"), 1L);
    }

    /**
     * Method under test: {@link UserServiceImpl#updateUser(UserUpdateDtoRequest, Long)}
     */
    @Test
    void testUpdateUser11() {
        when(userRepository.save((User) any())).thenReturn(mock(User.class));
        when(userRepository.findByUsername((String) any())).thenReturn(Optional.empty());
        when(userRepository.findById((Long) any())).thenReturn(Optional.of(new User()));
        new ResourceNotFoundException("Resource Name", "Field Name", "42");

        new ResourceNotFoundException("Resource Name", "Field Name", "42");

        new ResourceNotFoundException("Resource Name", "Field Name", "42");

        when(authenticationFacade.getAuthentication())
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        assertThrows(ResourceNotFoundException.class,
                () -> userServiceImpl.updateUser(new UserUpdateDtoRequest("janedoe"), 1L));
        verify(userRepository).findByUsername((String) any());
        verify(userRepository).findById((Long) any());
        verify(authenticationFacade).getAuthentication();
    }

    /**
     * Method under test: {@link UserServiceImpl#updateUser(UserUpdateDtoRequest, Long)}
     */
    @Test
    void testUpdateUser12() {
        User user = mock(User.class);
        doThrow(new AppException(HttpStatus.CONTINUE, "An error occurred")).when(user).setUsername((String) any());
        Optional<User> ofResult = Optional.of(user);

        User user1 = new User();
        user1.setRole(new Role(1L, "ROLE_ADMIN"));
        Optional<User> ofResult1 = Optional.of(user1);
        when(userRepository.save((User) any())).thenReturn(mock(User.class));
        when(userRepository.findByUsername((String) any())).thenReturn(ofResult1);
        when(userRepository.findById((Long) any())).thenReturn(ofResult);
        new ResourceNotFoundException("Resource Name", "Field Name", "42");

        new ResourceNotFoundException("Resource Name", "Field Name", "42");

        new ResourceNotFoundException("Resource Name", "Field Name", "42");

        when(authenticationFacade.getAuthentication())
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        assertThrows(AppException.class, () -> userServiceImpl.updateUser(new UserUpdateDtoRequest("janedoe"), 1L));
        verify(userRepository).findByUsername((String) any());
        verify(userRepository).findById((Long) any());
        verify(user).setUsername((String) any());
        verify(authenticationFacade).getAuthentication();
    }

    /**
     * Method under test: {@link UserServiceImpl#updateUser(UserUpdateDtoRequest, Long)}
     */
    @Test
    void testUpdateUser13() {
        User user = mock(User.class);
        when(user.getUsername()).thenReturn("janedoe");
        doThrow(new AppException(HttpStatus.CONTINUE, "An error occurred")).when(user).setUsername((String) any());
        Optional<User> ofResult = Optional.of(user);

        User user1 = new User();
        user1.setRole(new Role(1L, "Name"));
        Optional<User> ofResult1 = Optional.of(user1);
        when(userRepository.save((User) any())).thenReturn(mock(User.class));
        when(userRepository.findByUsername((String) any())).thenReturn(ofResult1);
        when(userRepository.findById((Long) any())).thenReturn(ofResult);
        new ResourceNotFoundException("Resource Name", "Field Name", "42");

        new ResourceNotFoundException("Resource Name", "Field Name", "42");

        new ResourceNotFoundException("Resource Name", "Field Name", "42");

        when(authenticationFacade.getAuthentication())
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        assertThrows(AppException.class, () -> userServiceImpl.updateUser(new UserUpdateDtoRequest("janedoe"), 1L));
        verify(userRepository).findByUsername((String) any());
        verify(userRepository).findById((Long) any());
        verify(user).getUsername();
        verify(authenticationFacade, atLeast(1)).getAuthentication();
    }

    /**
     * Method under test:
     * {@link UserServiceImpl#updatePassword(Long, UserPasswordDtoRequest)}
     */
    @Test
    void testUpdatePassword() {
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
        Optional<User> ofResult2 = Optional.of(user2);
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(ofResult2);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(authenticationFacade.getAuthentication())
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        assertThrows(AppException.class,
                () -> userServiceImpl.updatePassword(1L, new UserPasswordDtoRequest("iloveyou", "iloveyou")));
        verify(authenticationFacade, atLeast(1)).getAuthentication();
        verify(userRepository).findByUsername(Mockito.<String>any());
        verify(userRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test:
     * {@link UserServiceImpl#updatePassword(Long, UserPasswordDtoRequest)}
     */
    @Test
    void testUpdatePassword2() {
        Role role = new Role();
        role.setId(1L);
        role.setName("Name");

        User user = new User();
        user.setId(1L);
        user.setPassword("iloveyou");
        user.setRole(role);
        user.setUsername("janedoe");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findByUsername(Mockito.<String>any()))
                .thenThrow(new AppException(HttpStatus.CONTINUE, "An error occurred"));
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(authenticationFacade.getAuthentication())
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        assertThrows(AppException.class,
                () -> userServiceImpl.updatePassword(1L, new UserPasswordDtoRequest("iloveyou", "iloveyou")));
        verify(authenticationFacade).getAuthentication();
        verify(userRepository).findByUsername(Mockito.<String>any());
        verify(userRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test:
     * {@link UserServiceImpl#updatePassword(Long, UserPasswordDtoRequest)}
     */
    @Test
    void testUpdatePassword3() {
        Role role = new Role();
        role.setId(1L);
        role.setName("Name");

        User user = new User();
        user.setId(1L);
        user.setPassword("iloveyou");
        user.setRole(role);
        user.setUsername("janedoe");
        Optional<User> ofResult = Optional.of(user);
        Optional<User> emptyResult = Optional.empty();
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(emptyResult);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(authenticationFacade.getAuthentication())
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        assertThrows(ResourceNotFoundException.class,
                () -> userServiceImpl.updatePassword(1L, new UserPasswordDtoRequest("iloveyou", "iloveyou")));
        verify(authenticationFacade).getAuthentication();
        verify(userRepository).findByUsername(Mockito.<String>any());
        verify(userRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test:
     * {@link UserServiceImpl#updatePassword(Long, UserPasswordDtoRequest)}
     */
    @Test
    void testUpdatePassword4() {
        Optional<User> emptyResult = Optional.empty();
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);
        Role role = mock(Role.class);
        doNothing().when(role).setId(Mockito.<Long>any());
        doNothing().when(role).setName(Mockito.<String>any());
        role.setId(1L);
        role.setName("Name");

        Role role2 = new Role();
        role2.setId(1L);
        role2.setName("Name");
        User user = mock(User.class);
        doNothing().when(user).setId(Mockito.<Long>any());
        doNothing().when(user).setPassword(Mockito.<String>any());
        doNothing().when(user).setRole(Mockito.<Role>any());
        doNothing().when(user).setUsername(Mockito.<String>any());
        user.setId(1L);
        user.setPassword("iloveyou");
        user.setRole(role2);
        user.setUsername("janedoe");
        Optional.of(user);
        assertThrows(ResourceNotFoundException.class,
                () -> userServiceImpl.updatePassword(1L, new UserPasswordDtoRequest("iloveyou", "iloveyou")));
        verify(role).setId(Mockito.<Long>any());
        verify(role).setName(Mockito.<String>any());
        verify(user).setId(Mockito.<Long>any());
        verify(user).setPassword(Mockito.<String>any());
        verify(user).setRole(Mockito.<Role>any());
        verify(user).setUsername(Mockito.<String>any());
        verify(userRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link UserServiceImpl#updatePassword(Long, UserPasswordDtoRequest)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdatePassword5() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.gl.parcauto.entity.Role.getName()" because the return value of "com.gl.parcauto.entity.User.getRole()" is null
        //       at com.gl.parcauto.service.impl.UserServiceImpl.isAdmin(UserServiceImpl.java:180)
        //       at com.gl.parcauto.service.impl.UserServiceImpl.updatePassword(UserServiceImpl.java:116)
        //   See https://diff.blue/R013 to resolve this issue.

        when(userRepository.findByUsername((String) any())).thenReturn(Optional.of(new User()));
        when(userRepository.findById((Long) any())).thenReturn(Optional.of(new User()));
        when(authenticationFacade.getAuthentication())
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        userServiceImpl.updatePassword(1L, new UserPasswordDtoRequest("iloveyou", "iloveyou"));
    }

    /**
     * Method under test: {@link UserServiceImpl#updatePassword(Long, UserPasswordDtoRequest)}
     */
    @Test
    void testUpdatePassword6() {
        when(userRepository.findByUsername((String) any()))
                .thenThrow(new AppException(HttpStatus.CONTINUE, "An error occurred"));
        when(userRepository.findById((Long) any())).thenReturn(Optional.of(new User()));
        when(authenticationFacade.getAuthentication())
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        assertThrows(AppException.class,
                () -> userServiceImpl.updatePassword(1L, new UserPasswordDtoRequest("iloveyou", "iloveyou")));
        verify(userRepository).findByUsername((String) any());
        verify(userRepository).findById((Long) any());
        verify(authenticationFacade).getAuthentication();
    }

    /**
     * Method under test: {@link UserServiceImpl#updatePassword(Long, UserPasswordDtoRequest)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdatePassword7() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException
        //   See https://diff.blue/R013 to resolve this issue.

        User user = new User();
        user.setRole(new Role());
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findByUsername((String) any())).thenReturn(ofResult);
        when(userRepository.findById((Long) any())).thenReturn(Optional.of(new User()));
        when(authenticationFacade.getAuthentication())
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        userServiceImpl.updatePassword(1L, new UserPasswordDtoRequest("iloveyou", "iloveyou"));
    }

    /**
     * Method under test: {@link UserServiceImpl#updatePassword(Long, UserPasswordDtoRequest)}
     */
    @Test
    void testUpdatePassword8() {
        User user = new User();
        user.setRole(new Role(1L, "ROLE_ADMIN"));
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.save((User) any())).thenReturn(new User());
        when(userRepository.findByUsername((String) any())).thenReturn(ofResult);
        when(userRepository.findById((Long) any())).thenReturn(Optional.of(new User()));
        when(authenticationFacade.getAuthentication())
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        when(passwordEncoder.encode((CharSequence) any())).thenReturn("secret");
        userServiceImpl.updatePassword(1L, new UserPasswordDtoRequest("iloveyou", "iloveyou"));
        verify(userRepository).save((User) any());
        verify(userRepository).findByUsername((String) any());
        verify(userRepository).findById((Long) any());
        verify(authenticationFacade).getAuthentication();
        verify(passwordEncoder).encode((CharSequence) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#updatePassword(Long, UserPasswordDtoRequest)}
     */
    @Test
    void testUpdatePassword9() {
        User user = new User();
        user.setRole(new Role(1L, "ROLE_ADMIN"));
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.save((User) any())).thenReturn(new User());
        when(userRepository.findByUsername((String) any())).thenReturn(ofResult);
        when(userRepository.findById((Long) any())).thenReturn(Optional.of(new User()));
        when(authenticationFacade.getAuthentication())
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        when(passwordEncoder.encode((CharSequence) any()))
                .thenThrow(new AppException(HttpStatus.CONTINUE, "An error occurred"));
        assertThrows(AppException.class,
                () -> userServiceImpl.updatePassword(1L, new UserPasswordDtoRequest("iloveyou", "iloveyou")));
        verify(userRepository).findByUsername((String) any());
        verify(userRepository).findById((Long) any());
        verify(authenticationFacade).getAuthentication();
        verify(passwordEncoder).encode((CharSequence) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#updatePassword(Long, UserPasswordDtoRequest)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdatePassword10() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException
        //   See https://diff.blue/R013 to resolve this issue.

        User user = new User();
        user.setRole(new Role(1L, "iloveyou"));
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.save((User) any())).thenReturn(new User());
        when(userRepository.findByUsername((String) any())).thenReturn(ofResult);
        when(userRepository.findById((Long) any())).thenReturn(Optional.of(new User()));
        when(authenticationFacade.getAuthentication())
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        when(passwordEncoder.encode((CharSequence) any())).thenReturn("secret");
        userServiceImpl.updatePassword(1L, new UserPasswordDtoRequest("iloveyou", "iloveyou"));
    }

    /**
     * Method under test: {@link UserServiceImpl#updatePassword(Long, UserPasswordDtoRequest)}
     */
    @Test
    void testUpdatePassword11() {
        when(userRepository.save((User) any())).thenReturn(new User());
        when(userRepository.findByUsername((String) any())).thenReturn(Optional.empty());
        when(userRepository.findById((Long) any())).thenReturn(Optional.of(new User()));
        when(authenticationFacade.getAuthentication())
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        when(passwordEncoder.encode((CharSequence) any())).thenReturn("secret");
        assertThrows(ResourceNotFoundException.class,
                () -> userServiceImpl.updatePassword(1L, new UserPasswordDtoRequest("iloveyou", "iloveyou")));
        verify(userRepository).findByUsername((String) any());
        verify(userRepository).findById((Long) any());
        verify(authenticationFacade).getAuthentication();
    }

    /**
     * Method under test: {@link UserServiceImpl#updatePassword(Long, UserPasswordDtoRequest)}
     */
    @Test
    void testUpdatePassword12() {
        User user = mock(User.class);
        doThrow(new AppException(HttpStatus.CONTINUE, "An error occurred")).when(user).setPassword((String) any());
        Optional<User> ofResult = Optional.of(user);

        User user1 = new User();
        user1.setRole(new Role(1L, "ROLE_ADMIN"));
        Optional<User> ofResult1 = Optional.of(user1);
        when(userRepository.save((User) any())).thenReturn(new User());
        when(userRepository.findByUsername((String) any())).thenReturn(ofResult1);
        when(userRepository.findById((Long) any())).thenReturn(ofResult);
        when(authenticationFacade.getAuthentication())
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        when(passwordEncoder.encode((CharSequence) any())).thenReturn("secret");
        assertThrows(AppException.class,
                () -> userServiceImpl.updatePassword(1L, new UserPasswordDtoRequest("iloveyou", "iloveyou")));
        verify(userRepository).findByUsername((String) any());
        verify(userRepository).findById((Long) any());
        verify(user).setPassword((String) any());
        verify(authenticationFacade).getAuthentication();
        verify(passwordEncoder).encode((CharSequence) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#updatePassword(Long, UserPasswordDtoRequest)}
     */
    @Test
    void testUpdatePassword13() {
        User user = mock(User.class);
        when(user.getUsername()).thenReturn("janedoe");
        doThrow(new AppException(HttpStatus.CONTINUE, "An error occurred")).when(user).setPassword((String) any());
        Optional<User> ofResult = Optional.of(user);

        User user1 = new User();
        user1.setRole(new Role(1L, "iloveyou"));
        Optional<User> ofResult1 = Optional.of(user1);
        when(userRepository.save((User) any())).thenReturn(new User());
        when(userRepository.findByUsername((String) any())).thenReturn(ofResult1);
        when(userRepository.findById((Long) any())).thenReturn(ofResult);
        when(authenticationFacade.getAuthentication())
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        when(passwordEncoder.encode((CharSequence) any())).thenReturn("secret");
        assertThrows(AppException.class,
                () -> userServiceImpl.updatePassword(1L, new UserPasswordDtoRequest("iloveyou", "iloveyou")));
        verify(userRepository).findByUsername((String) any());
        verify(userRepository).findById((Long) any());
        verify(user).getUsername();
        verify(authenticationFacade, atLeast(1)).getAuthentication();
    }

    /**
     * Method under test: {@link UserServiceImpl#updatePassword(Long, UserPasswordDtoRequest)}
     */
    @Test
    void testUpdatePassword14() {
        User user = mock(User.class);
        when(user.getUsername()).thenReturn("Principal");
        doThrow(new AppException(HttpStatus.CONTINUE, "An error occurred")).when(user).setPassword((String) any());
        Optional<User> ofResult = Optional.of(user);

        User user1 = new User();
        user1.setRole(new Role(1L, "iloveyou"));
        Optional<User> ofResult1 = Optional.of(user1);
        when(userRepository.save((User) any())).thenReturn(new User());
        when(userRepository.findByUsername((String) any())).thenReturn(ofResult1);
        when(userRepository.findById((Long) any())).thenReturn(ofResult);
        when(authenticationFacade.getAuthentication())
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        when(passwordEncoder.encode((CharSequence) any())).thenReturn("secret");
        assertThrows(AppException.class,
                () -> userServiceImpl.updatePassword(1L, new UserPasswordDtoRequest("iloveyou", "iloveyou")));
        verify(userRepository).findByUsername((String) any());
        verify(userRepository).findById((Long) any());
        verify(user).getUsername();
        verify(user).setPassword((String) any());
        verify(authenticationFacade, atLeast(1)).getAuthentication();
        verify(passwordEncoder).encode((CharSequence) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#updatePassword(Long, UserPasswordDtoRequest)}
     */
    @Test
    void testUpdatePassword15() {
        User user = new User();
        user.setRole(new Role(1L, "iloveyou"));
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.save((User) any())).thenReturn(new User());
        when(userRepository.findByUsername((String) any())).thenReturn(ofResult);
        when(userRepository.findById((Long) any())).thenReturn(Optional.empty());
        new AppException(HttpStatus.CONTINUE, "An error occurred");

        when(authenticationFacade.getAuthentication())
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        when(passwordEncoder.encode((CharSequence) any())).thenReturn("secret");
        assertThrows(ResourceNotFoundException.class,
                () -> userServiceImpl.updatePassword(1L, new UserPasswordDtoRequest("iloveyou", "iloveyou")));
        verify(userRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#updatePassword(Long, UserPasswordDtoRequest)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdatePassword16() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "org.springframework.security.core.Authentication.getName()" because the return value of "com.gl.parcauto.auth.AuthenticationFacade.getAuthentication()" is null
        //       at com.gl.parcauto.service.impl.UserServiceImpl.isAdmin(UserServiceImpl.java:176)
        //       at com.gl.parcauto.service.impl.UserServiceImpl.updatePassword(UserServiceImpl.java:116)
        //   See https://diff.blue/R013 to resolve this issue.

        User user = mock(User.class);
        when(user.getUsername()).thenReturn("janedoe");
        doThrow(new AppException(HttpStatus.CONTINUE, "An error occurred")).when(user).setPassword((String) any());
        Optional<User> ofResult = Optional.of(user);

        User user1 = new User();
        user1.setRole(new Role(1L, "iloveyou"));
        Optional<User> ofResult1 = Optional.of(user1);
        when(userRepository.save((User) any())).thenReturn(new User());
        when(userRepository.findByUsername((String) any())).thenReturn(ofResult1);
        when(userRepository.findById((Long) any())).thenReturn(ofResult);
        when(authenticationFacade.getAuthentication()).thenReturn(null);
        when(passwordEncoder.encode((CharSequence) any())).thenReturn("secret");
        userServiceImpl.updatePassword(1L, new UserPasswordDtoRequest("iloveyou", "iloveyou"));
    }

    /**
     * Method under test:
     * {@link UserServiceImpl#updateRoleUser(Long, RoleDtoRequest)}
     */
    @Test
    void testUpdateRoleUser() {
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
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Role role3 = new Role();
        role3.setId(1L);
        role3.setName("Name");
        Optional<Role> ofResult2 = Optional.of(role3);
        when(roleRepository.findByName(Mockito.<String>any())).thenReturn(ofResult2);
        UserDtoResponse actualUpdateRoleUserResult = userServiceImpl.updateRoleUser(1L, new RoleDtoRequest("Name"));
        verify(roleRepository).findByName(Mockito.<String>any());
        verify(userRepository).findById(Mockito.<Long>any());
        verify(userRepository).save(Mockito.<User>any());
        RoleDtoResponse roleResult = actualUpdateRoleUserResult.role();
        assertEquals("Name", roleResult.name());
        assertEquals("janedoe", actualUpdateRoleUserResult.username());
        assertEquals(1L, roleResult.id().longValue());
        assertEquals(1L, actualUpdateRoleUserResult.id().longValue());
    }

    /**
     * Method under test:
     * {@link UserServiceImpl#updateRoleUser(Long, RoleDtoRequest)}
     */
    @Test
    void testUpdateRoleUser2() {
        Role role = new Role();
        role.setId(1L);
        role.setName("Name");

        User user = new User();
        user.setId(1L);
        user.setPassword("iloveyou");
        user.setRole(role);
        user.setUsername("janedoe");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.save(Mockito.<User>any()))
                .thenThrow(new AppException(HttpStatus.CONTINUE, "An error occurred"));
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Role role2 = new Role();
        role2.setId(1L);
        role2.setName("Name");
        Optional<Role> ofResult2 = Optional.of(role2);
        when(roleRepository.findByName(Mockito.<String>any())).thenReturn(ofResult2);
        assertThrows(AppException.class, () -> userServiceImpl.updateRoleUser(1L, new RoleDtoRequest("Name")));
        verify(roleRepository).findByName(Mockito.<String>any());
        verify(userRepository).findById(Mockito.<Long>any());
        verify(userRepository).save(Mockito.<User>any());
    }

    /**
     * Method under test:
     * {@link UserServiceImpl#updateRoleUser(Long, RoleDtoRequest)}
     */
    @Test
    void testUpdateRoleUser3() {
        Optional<User> emptyResult = Optional.empty();
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);
        assertThrows(ResourceNotFoundException.class, () -> userServiceImpl.updateRoleUser(1L, new RoleDtoRequest("Name")));
        verify(userRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test:
     * {@link UserServiceImpl#updateRoleUser(Long, RoleDtoRequest)}
     */
    @Test
    void testUpdateRoleUser4() {
        Role role = new Role();
        role.setId(1L);
        role.setName("Name");

        User user = new User();
        user.setId(1L);
        user.setPassword("iloveyou");
        user.setRole(role);
        user.setUsername("janedoe");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        Optional<Role> emptyResult = Optional.empty();
        when(roleRepository.findByName(Mockito.<String>any())).thenReturn(emptyResult);
        assertThrows(ResourceNotFoundException.class, () -> userServiceImpl.updateRoleUser(1L, new RoleDtoRequest("Name")));
        verify(roleRepository).findByName(Mockito.<String>any());
        verify(userRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link UserServiceImpl#updateRoleUser(Long, RoleDtoRequest)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateRoleUser5() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException
        //   See https://diff.blue/R013 to resolve this issue.

        when(userRepository.findById((Long) any())).thenReturn(Optional.of(new User()));
        when(roleRepository.findByName((String) any())).thenReturn(Optional.of(new Role()));
        userServiceImpl.updateRoleUser(1L, new RoleDtoRequest("Name"));
    }

    /**
     * Method under test: {@link UserServiceImpl#updateRoleUser(Long, RoleDtoRequest)}
     */
    @Test
    void testUpdateRoleUser6() {
        when(userRepository.findById((Long) any())).thenReturn(Optional.of(new User()));
        when(roleRepository.findByName((String) any()))
                .thenThrow(new AppException(HttpStatus.CONTINUE, "An error occurred"));
        assertThrows(AppException.class, () -> userServiceImpl.updateRoleUser(1L, new RoleDtoRequest("Name")));
        verify(userRepository).findById((Long) any());
        verify(roleRepository).findByName((String) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#updateRoleUser(Long, RoleDtoRequest)}
     */
    @Test
    void testUpdateRoleUser7() {
        when(userRepository.findById((Long) any())).thenReturn(Optional.empty());
        when(roleRepository.findByName((String) any())).thenReturn(Optional.of(new Role()));
        assertThrows(ResourceNotFoundException.class,
                () -> userServiceImpl.updateRoleUser(1L, new RoleDtoRequest("Name")));
        verify(userRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#updateRoleUser(Long, RoleDtoRequest)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateRoleUser8() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.gl.parcauto.entity.Role.getName()" because the return value of "com.gl.parcauto.entity.User.getRole()" is null
        //       at com.gl.parcauto.service.impl.UserServiceImpl.isAdmin(UserServiceImpl.java:180)
        //       at com.gl.parcauto.service.impl.UserServiceImpl.addRoleToUser(UserServiceImpl.java:168)
        //       at com.gl.parcauto.service.impl.UserServiceImpl.updateRoleUser(UserServiceImpl.java:135)
        //   See https://diff.blue/R013 to resolve this issue.

        when(userRepository.findByUsername((String) any())).thenReturn(Optional.of(new User()));
        when(userRepository.findById((Long) any())).thenReturn(Optional.of(new User()));
        when(roleRepository.findByName((String) any())).thenReturn(Optional.of(new Role(1L, "ROLE_ADMIN")));
        when(authenticationFacade.getAuthentication())
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        userServiceImpl.updateRoleUser(1L, new RoleDtoRequest("Name"));
    }

    /**
     * Method under test: {@link UserServiceImpl#updateRoleUser(Long, RoleDtoRequest)}
     */
    @Test
    void testUpdateRoleUser9() {
        when(userRepository.findByUsername((String) any()))
                .thenThrow(new AppException(HttpStatus.CONTINUE, "An error occurred"));
        when(userRepository.findById((Long) any())).thenReturn(Optional.of(new User()));
        when(roleRepository.findByName((String) any())).thenReturn(Optional.of(new Role(1L, "ROLE_ADMIN")));
        when(authenticationFacade.getAuthentication())
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        assertThrows(AppException.class, () -> userServiceImpl.updateRoleUser(1L, new RoleDtoRequest("Name")));
        verify(userRepository).findByUsername((String) any());
        verify(userRepository).findById((Long) any());
        verify(roleRepository).findByName((String) any());
        verify(authenticationFacade).getAuthentication();
    }

    /**
     * Method under test: {@link UserServiceImpl#updateRoleUser(Long, RoleDtoRequest)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateRoleUser10() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "String.equals(Object)" because "str" is null
        //       at com.gl.parcauto.service.impl.UserServiceImpl.isAdmin(UserServiceImpl.java:180)
        //       at com.gl.parcauto.service.impl.UserServiceImpl.addRoleToUser(UserServiceImpl.java:168)
        //       at com.gl.parcauto.service.impl.UserServiceImpl.updateRoleUser(UserServiceImpl.java:135)
        //   See https://diff.blue/R013 to resolve this issue.

        User user = new User();
        user.setRole(new Role());
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findByUsername((String) any())).thenReturn(ofResult);
        when(userRepository.findById((Long) any())).thenReturn(Optional.of(new User()));
        when(roleRepository.findByName((String) any())).thenReturn(Optional.of(new Role(1L, "ROLE_ADMIN")));
        when(authenticationFacade.getAuthentication())
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        userServiceImpl.updateRoleUser(1L, new RoleDtoRequest("Name"));
    }

    /**
     * Method under test: {@link UserServiceImpl#updateRoleUser(Long, RoleDtoRequest)}
     */
    @Test
    void testUpdateRoleUser11() {
        User user = new User();
        user.setRole(new Role(1L, "ROLE_ADMIN"));
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.save((User) any())).thenReturn(new User());
        when(userRepository.findByUsername((String) any())).thenReturn(ofResult);
        when(userRepository.findById((Long) any())).thenReturn(Optional.of(new User()));
        when(roleRepository.findByName((String) any())).thenReturn(Optional.of(new Role(1L, "ROLE_ADMIN")));
        when(authenticationFacade.getAuthentication())
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        UserDtoResponse actualUpdateRoleUserResult = userServiceImpl.updateRoleUser(1L, new RoleDtoRequest("Name"));
        assertNull(actualUpdateRoleUserResult.id());
        assertNull(actualUpdateRoleUserResult.username());
        assertNull(actualUpdateRoleUserResult.role());
        verify(userRepository).save((User) any());
        verify(userRepository).findByUsername((String) any());
        verify(userRepository).findById((Long) any());
        verify(roleRepository).findByName((String) any());
        verify(authenticationFacade).getAuthentication();
    }

    /**
     * Method under test: {@link UserServiceImpl#updateRoleUser(Long, RoleDtoRequest)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateRoleUser12() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.gl.parcauto.exception.ResourceNotFoundException: Resource Name not found with Field Name : '42'
        //       at com.gl.parcauto.mapper.UserMapperImpl.userToUserDtoResponse(UserMapperImpl.java:59)
        //       at com.gl.parcauto.service.impl.UserServiceImpl.updateRoleUser(UserServiceImpl.java:140)
        //   See https://diff.blue/R013 to resolve this issue.

        User user = new User();
        user.setRole(new Role(1L, "ROLE_ADMIN"));
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.save((User) any())).thenReturn(mock(User.class));
        when(userRepository.findByUsername((String) any())).thenReturn(ofResult);
        when(userRepository.findById((Long) any())).thenReturn(Optional.of(new User()));
        new ResourceNotFoundException("Resource Name", "Field Name", "42");

        new ResourceNotFoundException("Resource Name", "Field Name", "42");

        new ResourceNotFoundException("Resource Name", "Field Name", "42");

        when(roleRepository.findByName((String) any())).thenReturn(Optional.of(new Role(1L, "ROLE_ADMIN")));
        when(authenticationFacade.getAuthentication())
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        userServiceImpl.updateRoleUser(1L, new RoleDtoRequest("Name"));
    }

    /**
     * Method under test: {@link UserServiceImpl#updateRoleUser(Long, RoleDtoRequest)}
     */
    @Test
    void testUpdateRoleUser13() {
        User user = new User();
        user.setRole(new Role(1L, "Name"));
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.save((User) any())).thenReturn(mock(User.class));
        when(userRepository.findByUsername((String) any())).thenReturn(ofResult);
        when(userRepository.findById((Long) any())).thenReturn(Optional.of(new User()));
        new ResourceNotFoundException("Resource Name", "Field Name", "42");

        new ResourceNotFoundException("Resource Name", "Field Name", "42");

        new ResourceNotFoundException("Resource Name", "Field Name", "42");

        when(roleRepository.findByName((String) any())).thenReturn(Optional.of(new Role(1L, "ROLE_ADMIN")));
        when(authenticationFacade.getAuthentication())
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        assertThrows(AppException.class, () -> userServiceImpl.updateRoleUser(1L, new RoleDtoRequest("Name")));
        verify(userRepository).findByUsername((String) any());
        verify(userRepository).findById((Long) any());
        verify(roleRepository).findByName((String) any());
        verify(authenticationFacade).getAuthentication();
    }

    /**
     * Method under test: {@link UserServiceImpl#updateRoleUser(Long, RoleDtoRequest)}
     */
    @Test
    void testUpdateRoleUser14() {
        when(userRepository.save((User) any())).thenReturn(mock(User.class));
        when(userRepository.findByUsername((String) any())).thenReturn(Optional.empty());
        when(userRepository.findById((Long) any())).thenReturn(Optional.of(new User()));
        new ResourceNotFoundException("Resource Name", "Field Name", "42");

        new ResourceNotFoundException("Resource Name", "Field Name", "42");

        new ResourceNotFoundException("Resource Name", "Field Name", "42");

        when(roleRepository.findByName((String) any())).thenReturn(Optional.of(new Role(1L, "ROLE_ADMIN")));
        when(authenticationFacade.getAuthentication())
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        assertThrows(ResourceNotFoundException.class,
                () -> userServiceImpl.updateRoleUser(1L, new RoleDtoRequest("Name")));
        verify(userRepository).findByUsername((String) any());
        verify(userRepository).findById((Long) any());
        verify(roleRepository).findByName((String) any());
        verify(authenticationFacade).getAuthentication();
    }

    /**
     * Method under test: {@link UserServiceImpl#updateRoleUser(Long, RoleDtoRequest)}
     */
    @Test
    void testUpdateRoleUser15() {
        User user = mock(User.class);
        doThrow(new AppException(HttpStatus.CONTINUE, "An error occurred")).when(user).setRole((Role) any());
        Optional<User> ofResult = Optional.of(user);

        User user1 = new User();
        user1.setRole(new Role(1L, "ROLE_ADMIN"));
        Optional<User> ofResult1 = Optional.of(user1);
        when(userRepository.save((User) any())).thenReturn(mock(User.class));
        when(userRepository.findByUsername((String) any())).thenReturn(ofResult1);
        when(userRepository.findById((Long) any())).thenReturn(ofResult);
        new ResourceNotFoundException("Resource Name", "Field Name", "42");

        new ResourceNotFoundException("Resource Name", "Field Name", "42");

        new ResourceNotFoundException("Resource Name", "Field Name", "42");

        when(roleRepository.findByName((String) any())).thenReturn(Optional.of(new Role(1L, "ROLE_ADMIN")));
        when(authenticationFacade.getAuthentication())
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        assertThrows(AppException.class, () -> userServiceImpl.updateRoleUser(1L, new RoleDtoRequest("Name")));
        verify(userRepository).findByUsername((String) any());
        verify(userRepository).findById((Long) any());
        verify(user).setRole((Role) any());
        verify(roleRepository).findByName((String) any());
        verify(authenticationFacade).getAuthentication();
    }

    /**
     * Method under test: {@link UserServiceImpl#deleteUser(Long)}
     */
    @Test
    void testDeleteUser() {
        Role role = new Role();
        role.setId(1L);
        role.setName("Name");

        User user = new User();
        user.setId(1L);
        user.setPassword("iloveyou");
        user.setRole(role);
        user.setUsername("janedoe");
        Optional<User> ofResult = Optional.of(user);
        doNothing().when(userRepository).delete(Mockito.<User>any());
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        userServiceImpl.deleteUser(1L);
        verify(userRepository).delete(Mockito.<User>any());
        verify(userRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link UserServiceImpl#deleteUser(Long)}
     */
    @Test
    void testDeleteUser2() {
        Role role = new Role();
        role.setId(1L);
        role.setName("Name");

        User user = new User();
        user.setId(1L);
        user.setPassword("iloveyou");
        user.setRole(role);
        user.setUsername("janedoe");
        Optional<User> ofResult = Optional.of(user);
        doThrow(new AppException(HttpStatus.CONTINUE, "An error occurred")).when(userRepository)
                .delete(Mockito.<User>any());
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertThrows(AppException.class, () -> userServiceImpl.deleteUser(1L));
        verify(userRepository).delete(Mockito.<User>any());
        verify(userRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link UserServiceImpl#deleteUser(Long)}
     */
    @Test
    void testDeleteUser3() {
        Optional<User> emptyResult = Optional.empty();
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);
        assertThrows(ResourceNotFoundException.class, () -> userServiceImpl.deleteUser(1L));
        verify(userRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link UserServiceImpl#deleteUser(String)}
     */
    @Test
    void testDeleteUser4() {
        Role role = new Role();
        role.setId(1L);
        role.setName("Name");

        User user = new User();
        user.setId(1L);
        user.setPassword("iloveyou");
        user.setRole(role);
        user.setUsername("janedoe");
        Optional<User> ofResult = Optional.of(user);
        doNothing().when(userRepository).delete(Mockito.<User>any());
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(ofResult);
        userServiceImpl.deleteUser("janedoe");
        verify(userRepository).findByUsername(Mockito.<String>any());
        verify(userRepository).delete(Mockito.<User>any());
    }

    /**
     * Method under test: {@link UserServiceImpl#deleteUser(String)}
     */
    @Test
    void testDeleteUser5() {
        Role role = new Role();
        role.setId(1L);
        role.setName("Name");

        User user = new User();
        user.setId(1L);
        user.setPassword("iloveyou");
        user.setRole(role);
        user.setUsername("janedoe");
        Optional<User> ofResult = Optional.of(user);
        doThrow(new AppException(HttpStatus.CONTINUE, "An error occurred")).when(userRepository)
                .delete(Mockito.<User>any());
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(ofResult);
        assertThrows(AppException.class, () -> userServiceImpl.deleteUser("janedoe"));
        verify(userRepository).findByUsername(Mockito.<String>any());
        verify(userRepository).delete(Mockito.<User>any());
    }

    /**
     * Method under test: {@link UserServiceImpl#deleteUser(String)}
     */
    @Test
    void testDeleteUser6() {
        Optional<User> emptyResult = Optional.empty();
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(emptyResult);
        assertThrows(ResourceNotFoundException.class, () -> userServiceImpl.deleteUser("janedoe"));
        verify(userRepository).findByUsername(Mockito.<String>any());
    }

    /**
     * Method under test: {@link UserServiceImpl#deleteUser(Long)}
     */
    @Test
    void testDeleteUser7() {
        doNothing().when(userRepository).delete((User) any());
        when(userRepository.findById((Long) any())).thenReturn(Optional.of(new User()));
        userServiceImpl.deleteUser(1L);
        verify(userRepository).findById((Long) any());
        verify(userRepository).delete((User) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#deleteUser(Long)}
     */
    @Test
    void testDeleteUser8() {
        doThrow(new AppException(HttpStatus.CONTINUE, "An error occurred")).when(userRepository).delete((User) any());
        when(userRepository.findById((Long) any())).thenReturn(Optional.of(new User()));
        assertThrows(AppException.class, () -> userServiceImpl.deleteUser(1L));
        verify(userRepository).findById((Long) any());
        verify(userRepository).delete((User) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#deleteUser(Long)}
     */
    @Test
    void testDeleteUser9() {
        doNothing().when(userRepository).delete((User) any());
        when(userRepository.findById((Long) any())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> userServiceImpl.deleteUser(1L));
        verify(userRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#deleteUser(String)}
     */
    @Test
    void testDeleteUser10() {
        doNothing().when(userRepository).delete((User) any());
        when(userRepository.findByUsername((String) any())).thenReturn(Optional.of(new User()));
        userServiceImpl.deleteUser("janedoe");
        verify(userRepository).findByUsername((String) any());
        verify(userRepository).delete((User) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#deleteUser(String)}
     */
    @Test
    void testDeleteUser11() {
        doThrow(new AppException(HttpStatus.CONTINUE, "An error occurred")).when(userRepository).delete((User) any());
        when(userRepository.findByUsername((String) any())).thenReturn(Optional.of(new User()));
        assertThrows(AppException.class, () -> userServiceImpl.deleteUser("janedoe"));
        verify(userRepository).findByUsername((String) any());
        verify(userRepository).delete((User) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#deleteUser(String)}
     */
    @Test
    void testDeleteUser12() {
        doNothing().when(userRepository).delete((User) any());
        when(userRepository.findByUsername((String) any())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> userServiceImpl.deleteUser("janedoe"));
        verify(userRepository).findByUsername((String) any());
    }
}
