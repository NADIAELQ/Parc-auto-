package com.gl.parcauto.controller;

import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gl.parcauto.auth.UserModel;
import com.gl.parcauto.dto.request.LoginRequestDto;
import com.gl.parcauto.dto.request.ResetPasswordDtoRequest;
import com.gl.parcauto.dto.response.RoleDtoResponse;
import com.gl.parcauto.dto.response.UserDtoResponse;
import com.gl.parcauto.entity.Role;
import com.gl.parcauto.service.AuthService;
import com.gl.parcauto.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {AuthController.class})
@ExtendWith(SpringExtension.class)
class AuthControllerDiffblueTest {
    @Autowired
    private AuthController authController;

    @MockBean
    private AuthService authService;

    @MockBean
    private UserService userService;

    /**
     * Method under test: {@link AuthController#getCurrentUser(UserModel)}
     */
    @Test
    void testGetCurrentUser() throws Exception {
        when(userService.getUserByUsername(Mockito.<String>any()))
                .thenReturn(new UserDtoResponse(1L, "janedoe", new RoleDtoResponse(1L, "Name")));
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/v1/auth/info");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("userModel",
                String.valueOf(new UserModel("janedoe", "iloveyou", new Role())));
        MockMvcBuilders.standaloneSetup(authController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":1,\"username\":\"janedoe\",\"role\":{\"id\":1,\"name\":\"Name\"}}"));
    }

    /**
     * Method under test: {@link AuthController#getCurrentUser(UserModel)}
     */
    @Test
    void testGetCurrentUser2() throws Exception {
        when(userService.getUserByUsername(Mockito.<String>any()))
                .thenReturn(new UserDtoResponse(1L, "janedoe", new RoleDtoResponse(1L, "Name")));
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/v1/auth/info");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("userModel", String.valueOf(""));
        MockMvcBuilders.standaloneSetup(authController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":1,\"username\":\"janedoe\",\"role\":{\"id\":1,\"name\":\"Name\"}}"));
    }

    /**
     * Method under test: {@link AuthController#loginHandler(LoginRequestDto)}
     */
    @Test
    void testLoginHandler() throws Exception {
        when(authService.login(Mockito.<LoginRequestDto>any())).thenReturn("Login");
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new LoginRequestDto("janedoe", "iloveyou")));
        MockMvcBuilders.standaloneSetup(authController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"token\":\"Login\",\"type\":\"Bearer\"}"));
    }

    /**
     * Method under test:
     * {@link AuthController#resetPassword(ResetPasswordDtoRequest)}
     */
    @Test
    void testResetPassword() throws Exception {
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.put("/api/v1/auth/reset-password")
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new ResetPasswordDtoRequest("iloveyou", "iloveyou", "iloveyou")));
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(authController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }
}
