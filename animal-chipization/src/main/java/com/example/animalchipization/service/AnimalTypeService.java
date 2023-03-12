package com.example.animalchipization.service;

import com.example.animalchipization.model.AnimalType;
import com.example.animalchipization.web.form.AnimalTypeForm;

public interface AnimalTypeService {

    AnimalType findAnimalTypeById(Long typeId);
    AnimalType addAnimalType(AnimalTypeForm animalTypeForm);
    AnimalType updateAnimalTypeById(Long animalTypeId, AnimalTypeForm animalTypeForm);
    void deleteAnimalTypeById(Long typeId);
}
