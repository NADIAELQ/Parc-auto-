package com.gl.parcauto.mapper;

import com.gl.parcauto.dto.request.TechnicalVisitDtoRequest;
import com.gl.parcauto.dto.response.TechnicalVisitDtoResponse;
import com.gl.parcauto.entity.TechnicalVisit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TechnicalVisitMapper {
    TechnicalVisitMapper INSTANCE = Mappers.getMapper(TechnicalVisitMapper.class);
    TechnicalVisit visitDtoRequestToTechnicalVisit(TechnicalVisitDtoRequest visitDtoRequest);
    @Mappings({
            @Mapping(target = "vehicleLicensePlate", source = "technicalVisit.vehicle_technical_visit.licensePlate"),
    })
    TechnicalVisitDtoResponse visitToTechnicalVisitDtoResponse(TechnicalVisit technicalVisit);
}
