package com.gl.parcauto.controller;

import com.gl.parcauto.auth.JwtAuthFilter;
import com.gl.parcauto.auth.UserModel;
import com.gl.parcauto.dto.TokenType;
import com.gl.parcauto.dto.request.LoginRequestDto;
import com.gl.parcauto.dto.request.RegisterDtoRequest;
import com.gl.parcauto.dto.request.ResetPasswordDtoRequest;
import com.gl.parcauto.dto.response.JwtAuthResponse;
import com.gl.parcauto.dto.response.UserDtoResponse;
import com.gl.parcauto.service.AuthService;
import com.gl.parcauto.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    // build login
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> loginHandler(
            @Valid @RequestBody LoginRequestDto loginDto) {
        String token = authService.login(loginDto);

        JwtAuthResponse response = new JwtAuthResponse(token, TokenType.Bearer);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @PostMapping("/register")
//    public ResponseEntity<JwtAuthResponse> registerHandler(
//            @Valid @RequestBody RegisterDtoRequest registerDtoRequest) {
//        String token = authService.register(registerDtoRequest);
//
//        JwtAuthResponse response = new JwtAuthResponse(token, TokenType.Bearer);
//
//        return ResponseEntity.ok(response);
//    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@Valid @RequestBody ResetPasswordDtoRequest dtoRequest) {
        authService.resetPassword(dtoRequest);
        return ResponseEntity.ok("Password updated successfully");
    }

    // get info authenticated user
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/info")
    public ResponseEntity<UserDtoResponse> getCurrentUser(
            @AuthenticationPrincipal UserModel userModel) {
        return ResponseEntity.ok(userService.getUserByUsername(userModel.getUsername()));
    }
}
