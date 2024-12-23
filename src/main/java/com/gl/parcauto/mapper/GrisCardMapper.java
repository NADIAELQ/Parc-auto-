package com.gl.parcauto.mapper;

import com.gl.parcauto.dto.request.GrisCardDtoRequest;
import com.gl.parcauto.dto.response.GrisCardDtoResponse;
import com.gl.parcauto.entity.GrisCard;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GrisCardMapper {
    GrisCardMapper INSTANCE = Mappers.getMapper(GrisCardMapper.class);
    GrisCard grisCardDtoRequestToGrisCard(GrisCardDtoRequest grisCardDtoRequest);
    GrisCardDtoResponse grisCardToGrisCardDtoResponse(GrisCard grisCard);
}
