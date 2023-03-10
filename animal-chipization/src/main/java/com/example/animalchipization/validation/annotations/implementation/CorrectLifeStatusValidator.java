package com.example.animalchipization.validation.annotations.implementation;

import com.example.animalchipization.validation.annotations.CorrectLifeStatus;
import jakarta.annotation.Nullable;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CorrectLifeStatusValidator implements ConstraintValidator<CorrectLifeStatus, String> {

    @Override
    public void initialize(CorrectLifeStatus correctLifeStatus) {
    }

    @Override
    public boolean isValid(@Nullable String lifeStatus, ConstraintValidatorContext constraintValidatorContext) {
        if (lifeStatus != null) {
            List<String> validValues = List.of("ALIVE", "DEAD");
            return validValues.stream().anyMatch(lifeStatus::equals);
        } else {
            return true;
        }
    }
}
