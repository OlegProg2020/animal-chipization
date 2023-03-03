package com.example.animalchipization.services.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CorrectGenderValidator.class)
@Target(value = {ElementType.PARAMETER})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface CorrectGender {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
