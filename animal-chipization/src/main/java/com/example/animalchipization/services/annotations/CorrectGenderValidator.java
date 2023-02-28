package com.example.animalchipization.services.annotations;

import jakarta.annotation.Nullable;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CorrectGenderValidator implements ConstraintValidator<CorrectGender, String> {

    @Override
    public void initialize(CorrectGender correctGender) {
    }

    @Override
    public boolean isValid(@Nullable String gender, ConstraintValidatorContext constraintValidatorContext) {
        if(gender != null) {
            List<String> validValues = List.of("MALE", "FEMALE", "OTHER");
            return validValues.stream().anyMatch(gender::equals);
        } else {
            return true;
        }
    }
}
