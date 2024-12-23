package com.gl.parcauto.mapper;

import com.gl.parcauto.dto.request.DriverLicenseDtoRequest;
import com.gl.parcauto.dto.response.DriverLicenseDtoResponse;
import com.gl.parcauto.entity.DriverLicense;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DriverLicenseMapper {
    DriverLicenseMapper INSTANCE = Mappers.getMapper(DriverLicenseMapper.class);
    DriverLicense licenseDtoRequestToDriverLicense(DriverLicenseDtoRequest licenseDto);
    @Mapping(target = "driverCin", source = "license.driver.cin")
    DriverLicenseDtoResponse licenseToDriverLicenseDtoResponse(DriverLicense license);
}
