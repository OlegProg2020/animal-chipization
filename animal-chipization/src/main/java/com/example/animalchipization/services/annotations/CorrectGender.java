package com.example.animalchipization.services.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Constraint(validatedBy = CorrectGenderValidator.class)
@Target(value = { ElementType.PARAMETER, ElementType.FIELD })
@Retention(value = RetentionPolicy.RUNTIME)
public @interface CorrectGender {
    String message() default "";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
