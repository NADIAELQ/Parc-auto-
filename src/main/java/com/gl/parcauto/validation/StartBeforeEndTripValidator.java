package com.gl.parcauto.validation;

import com.gl.parcauto.dto.request.TripDtoRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StartBeforeEndTripValidator implements ConstraintValidator<StartBeforeEnd, TripDtoRequest> {
    @Override
    public boolean isValid(TripDtoRequest dto, ConstraintValidatorContext context) {
        if(dto.startDate() == null || dto.endDate() == null)
            return true;
        return dto.startDate().isBefore(dto.endDate());
    }

    @Override
    public void initialize(StartBeforeEnd constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
