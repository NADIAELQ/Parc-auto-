package com.gl.parcauto.validation;

import com.gl.parcauto.entity.DriverLicense;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class DeliveryDateValidator implements ConstraintValidator<DeliveryDateValid, LocalDateTime> {
    @Override
    public boolean isValid(LocalDateTime deliveryDate, ConstraintValidatorContext context) {
        LocalDateTime currentDate = LocalDateTime.now();
        long diff = ChronoUnit.YEARS.between(deliveryDate, currentDate);

        return diff <= DriverLicense.EXPIRE_DATE_BY_YEAR;
    }

    @Override
    public void initialize(DeliveryDateValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
