package com.example.animalchipization.service;

import com.example.animalchipization.dto.AnimalTypeDto;

public interface AnimalTypeService {

    AnimalTypeDto findAnimalTypeById(Long typeId);

    AnimalTypeDto addAnimalType(AnimalTypeDto animalTypeDto);

    AnimalTypeDto updateAnimalType(AnimalTypeDto animalTypeDto);

    void deleteAnimalTypeById(Long typeId);
}
