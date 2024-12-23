package com.gl.parcauto.mapper;

import com.gl.parcauto.dto.request.RoleDtoRequest;
import com.gl.parcauto.dto.response.RoleDtoResponse;
import com.gl.parcauto.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);
    Role roleDtoRequestToRole(RoleDtoRequest roleDtoRequest);
    RoleDtoResponse roleToRoleDtoResponse(Role role);
}
