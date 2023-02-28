package com.example.animalchipization.services.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CorrectGenderValidator implements ConstraintValidator<CorrectGender, String> {

    @Override
    public void initialize(CorrectGender correctGender) {
    }

    @Override
    public boolean isValid(String gender, ConstraintValidatorContext constraintValidatorContext) {
        List<String> validValues = List.of("MALE", "FEMALE", "OTHER");
        return validValues.stream().anyMatch(gender::equals);
    }
}
