package com.example.animalchipization.service;

import com.example.animalchipization.model.AnimalType;

public interface AnimalTypeService {

    AnimalType findAnimalTypeById(Long typeId);

    AnimalType addAnimalType(AnimalType animalType);

    AnimalType updateAnimalType(AnimalType animalType);

    void deleteAnimalTypeById(Long typeId);
}
