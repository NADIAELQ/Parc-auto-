package com.gl.parcauto.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DeliveryDateValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DeliveryDateValid {
    String message() default "Delivery date is invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
