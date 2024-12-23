package com.gl.parcauto.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ActivatedDateValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivatedDateValid {
    String message() default "The activated date is invalid.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
