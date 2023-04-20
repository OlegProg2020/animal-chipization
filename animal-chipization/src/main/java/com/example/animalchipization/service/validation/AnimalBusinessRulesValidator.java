package com.example.animalchipization.service.validation;

import com.example.animalchipization.entity.Animal;

public interface AnimalBusinessRulesValidator {

    void validatePatchAnimal(Animal currentAnimal, Animal patchAnimal);

    void validateDeletingAnimal(Animal deletingAnimal);

}
