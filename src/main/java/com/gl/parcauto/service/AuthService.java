package com.gl.parcauto.service;


import com.gl.parcauto.dto.request.LoginRequestDto;
import com.gl.parcauto.dto.request.RegisterDtoRequest;
import com.gl.parcauto.dto.request.ResetPasswordDtoRequest;

public interface AuthService {
    String login(LoginRequestDto loginDto);

    String register(RegisterDtoRequest registerDtoRequest);

    void resetPassword(ResetPasswordDtoRequest dtoRequest);
}
