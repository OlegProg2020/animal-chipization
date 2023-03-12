package com.example.animalchipization.validation.annotations.implementation;

import com.example.animalchipization.data.repository.AnimalTypeRepository;
import com.example.animalchipization.exception.AnimalTypeWithThisTypeAlreadyExistsException;
import com.example.animalchipization.validation.annotations.UniqueAnimalType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UniqueAnimalTypeValidator implements ConstraintValidator<UniqueAnimalType, String> {

    private final AnimalTypeRepository animalTypeRepository;

    @Autowired
    public UniqueAnimalTypeValidator(AnimalTypeRepository animalTypeRepository) {
        this.animalTypeRepository = animalTypeRepository;
    }

    @Override
    public void initialize(UniqueAnimalType uniqueAnimalType) {
    }

    @Override
    public boolean isValid(String type, ConstraintValidatorContext constraintValidatorContext) {
        if (!animalTypeRepository.existsByType(type)) {
            return true;
        } else {
            throw new AnimalTypeWithThisTypeAlreadyExistsException();
        }
    }
}
