package com.example.animalchipization.validation.annotations.implementation;

import com.example.animalchipization.exception.DuplicateItemException;
import com.example.animalchipization.validation.annotations.UniqueElements;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class UniqueElementsValidator implements ConstraintValidator<UniqueElements, Collection<?>> {

    @Override
    public void initialize(UniqueElements uniqueElements) {
    }

    @Override
    public boolean isValid(Collection collection, ConstraintValidatorContext constraintValidatorContext) {
        if (collection.stream().distinct().count() == collection.size()) {
            return true;
        } else {
            throw new DuplicateItemException();
        }
    }
}
