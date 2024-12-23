package com.gl.parcauto.controller;

import com.gl.parcauto.dto.request.RoleDtoRequest;
import com.gl.parcauto.dto.request.UserDtoRequest;
import com.gl.parcauto.dto.request.UserPasswordDtoRequest;
import com.gl.parcauto.dto.request.UserUpdateDtoRequest;
import com.gl.parcauto.dto.response.UserDtoResponse;
import com.gl.parcauto.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class UserController {
    private final UserService userService;
    @PostMapping
    public ResponseEntity<UserDtoResponse> createUser(@Valid @RequestBody UserDtoRequest userDto) {
        UserDtoResponse response = userService.createUser(userDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserDtoResponse> getUserById(@PathVariable(name = "id") Long id) {
        UserDtoResponse response = userService.getUserById(id);
        return ResponseEntity.ok(response);
    }
    @GetMapping(params = {"username"})
    public ResponseEntity<UserDtoResponse> getUserByUsername(@RequestParam(name = "username") String username) {
        UserDtoResponse response = userService.getUserByUsername(username);
        return ResponseEntity.ok(response);
    }
    @GetMapping
    public ResponseEntity<List<UserDtoResponse>> getAllUsers() {
        List<UserDtoResponse> responses = userService.getAllUsers();
        return ResponseEntity.ok(responses);
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserDtoResponse> updateUser(
            @PathVariable(name = "id") Long id,
            @Valid @RequestBody UserUpdateDtoRequest userDtoRequest) {
        UserDtoResponse response = userService.updateUser(userDtoRequest, id);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/{id}/role")
    public ResponseEntity<UserDtoResponse> assignRoleToUser(
            @PathVariable(name = "id") Long id, @Valid @RequestBody RoleDtoRequest roleDto) {
        UserDtoResponse response = userService.updateRoleUser(id, roleDto);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/{id}/password")
    public ResponseEntity<String> updatePassword(
            @PathVariable(name = "id") Long id, @Valid @RequestBody UserPasswordDtoRequest passwordDtoRequest) {
        userService.updatePassword(id, passwordDtoRequest);
        return ResponseEntity.ok("User password updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable(name = "id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
