package com.example.animalchipization.validation.annotations;

import com.example.animalchipization.validation.annotations.implementation.CorrectGenderValidator;
import com.example.animalchipization.validation.annotations.implementation.UniqueAnimalTypeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueAnimalTypeValidator.class)
@Target(value = {ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface UniqueAnimalType {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
