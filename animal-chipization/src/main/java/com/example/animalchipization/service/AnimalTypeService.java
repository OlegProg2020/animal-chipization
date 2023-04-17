package com.example.animalchipization.service;

import com.example.animalchipization.dto.AnimalTypeDto;

public interface AnimalTypeService {

    AnimalTypeDto findById(Long id);

    AnimalTypeDto save(AnimalTypeDto animalTypeDto);

    AnimalTypeDto update(AnimalTypeDto animalTypeDto);

    void deleteById(Long id);
}
