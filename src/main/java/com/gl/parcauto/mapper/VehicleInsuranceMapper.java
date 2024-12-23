package com.gl.parcauto.mapper;

import com.gl.parcauto.dto.request.VehicleInsuranceDtoRequest;
import com.gl.parcauto.dto.response.VehicleInsuranceDtoResponse;
import com.gl.parcauto.entity.VehicleInsurance;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VehicleInsuranceMapper {
    VehicleInsuranceMapper INSTANCE = Mappers.getMapper(VehicleInsuranceMapper.class);
    VehicleInsurance toEntity(VehicleInsuranceDtoRequest request);
    VehicleInsuranceDtoResponse toDto(VehicleInsurance vehicleInsurance);
}
