package com.gl.parcauto.validation;

import com.gl.parcauto.entity.GrisCard;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ActivatedDateValidator implements ConstraintValidator<ActivatedDateValid, LocalDate> {
    @Override
    public boolean isValid(LocalDate activatedDate, ConstraintValidatorContext context) {
        LocalDate currentDate = LocalDate.now();
        long diff = ChronoUnit.YEARS.between(activatedDate, currentDate);

        return diff <= GrisCard.REGISTRATION_VALIDITY_YEARS;
    }

    @Override
    public void initialize(ActivatedDateValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
