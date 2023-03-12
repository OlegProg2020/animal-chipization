package com.example.animalchipization.validation.annotations.implementation;

import com.example.animalchipization.validation.annotations.CorrectGender;
import jakarta.annotation.Nullable;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
public class CorrectGenderValidator implements ConstraintValidator<CorrectGender, String> {

    @Override
    public void initialize(CorrectGender correctGender) {
    }

    @Override
    public boolean isValid(@Nullable String gender, ConstraintValidatorContext constraintValidatorContext) {
        if (gender != null) {
            List<String> validValues = List.of("MALE", "FEMALE", "OTHER");
            return validValues.stream().anyMatch(gender::equals);
        } else {
            return true;
        }
    }
}
