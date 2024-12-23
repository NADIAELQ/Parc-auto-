package com.gl.parcauto.mapper;

import com.gl.parcauto.dto.request.RegisterDtoRequest;
import com.gl.parcauto.dto.request.UserDtoRequest;
import com.gl.parcauto.dto.response.UserDtoResponse;
import com.gl.parcauto.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    User userDtoRequestToUser(UserDtoRequest userDtoRequest);
    User registerDtoRequestToUser(RegisterDtoRequest registerDtoRequest);
    UserDtoResponse userToUserDtoResponse(User user);
}
