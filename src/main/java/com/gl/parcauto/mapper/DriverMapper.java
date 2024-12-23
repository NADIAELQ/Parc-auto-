package com.gl.parcauto.mapper;

import com.gl.parcauto.dto.request.DriverDtoRequest;
import com.gl.parcauto.dto.response.DriverDtoResponse;
import com.gl.parcauto.dto.response.DriverUserDtoResponse;
import com.gl.parcauto.entity.Driver;
import com.gl.parcauto.dto.response.DriverTripDtoResponse;
import com.gl.parcauto.entity.Trip;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DriverMapper {
    DriverMapper INSTANCE = Mappers.getMapper(DriverMapper.class);
    Driver driverDtoRequestToDriver(DriverDtoRequest driverDtoRequest);
    DriverDtoResponse driverToDriverDtoResponse(Driver driver);
    @Mappings
    ({
            @Mapping(target = "userId", source = "driver.user.id"),
            @Mapping(target = "username", source = "driver.user.username"),
            @Mapping(target = "roleName", source = "driver.user.role.name")
    })
    DriverUserDtoResponse driverToDriverUserDtoResponse(Driver driver);
    DriverTripDtoResponse tripToDriverTripDtoResponse(Trip trip);
}
