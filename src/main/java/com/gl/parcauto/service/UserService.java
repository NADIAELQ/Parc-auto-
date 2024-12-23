package com.gl.parcauto.service;


import com.gl.parcauto.dto.request.RoleDtoRequest;
import com.gl.parcauto.dto.request.UserDtoRequest;
import com.gl.parcauto.dto.request.UserPasswordDtoRequest;
import com.gl.parcauto.dto.request.UserUpdateDtoRequest;
import com.gl.parcauto.dto.response.UserDtoResponse;

import java.util.List;

public interface UserService {
    UserDtoResponse createUser(UserDtoRequest userDtoRequest);
    UserDtoResponse getUserById(Long userId);
    UserDtoResponse getUserByUsername(String username);
    List<UserDtoResponse> getAllUsers();
    UserDtoResponse updateUser(UserUpdateDtoRequest userDtoRequest, Long userId);
    UserDtoResponse updateRoleUser(Long userId, RoleDtoRequest roleDtoRequest);
    void updatePassword(Long userId, UserPasswordDtoRequest passwordDtoRequest);
    void deleteUser(Long userId);
    void deleteUser(String username);
}
