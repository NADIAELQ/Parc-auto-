package com.gl.parcauto.mapper;

import com.gl.parcauto.dto.request.VacationDtoRequest;
import com.gl.parcauto.dto.response.VacationDtoResponse;
import com.gl.parcauto.entity.Vacation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VacationMapper {
    VacationMapper INSTANCE = Mappers.getMapper(VacationMapper.class);
    Vacation vacationDtoRequestToVacation(VacationDtoRequest vacationDtoRequest);
    @Mapping(target = "driverCin", source = "vacation.driver.cin")
    VacationDtoResponse vacationToVacationDtoResponse(Vacation vacation);
}
