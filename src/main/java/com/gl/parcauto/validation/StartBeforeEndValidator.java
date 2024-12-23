package com.gl.parcauto.validation;

import com.gl.parcauto.dto.request.VacationDtoRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StartBeforeEndValidator implements ConstraintValidator<StartBeforeEnd, VacationDtoRequest> {
    @Override
    public boolean isValid(VacationDtoRequest dto, ConstraintValidatorContext context) {
        if(dto.start() == null || dto.end() == null)
            return true;
        return dto.start().isBefore(dto.end());
    }

    @Override
    public void initialize(StartBeforeEnd constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
