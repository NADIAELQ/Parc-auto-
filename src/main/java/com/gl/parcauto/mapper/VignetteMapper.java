package com.gl.parcauto.mapper;

import com.gl.parcauto.dto.request.VignetteDtoRequest;
import com.gl.parcauto.dto.response.VignetteDtoResponse;
import com.gl.parcauto.entity.Vignette;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VignetteMapper {
    VignetteMapper INSTANCE = Mappers.getMapper(VignetteMapper.class);
    Vignette vignetteDtoRequestToVignette(VignetteDtoRequest vignetteDtoRequest);
    VignetteDtoResponse vignetteToVignetteDtoResponse(Vignette vignette);
}
