package com.example.animalchipization.service;

import com.example.animalchipization.entity.enums.Gender;
import com.example.animalchipization.entity.enums.LifeStatus;
import com.example.animalchipization.web.dto.AnimalDto;
import com.example.animalchipization.web.dto.AnimalTypeDto;

import java.time.ZonedDateTime;
import java.util.Collection;

public interface AnimalService {

    AnimalDto findById(Long animalId);

    Collection<AnimalDto> searchForAnimals(ZonedDateTime startDateTime, ZonedDateTime endDateTime,
                                           Long chipperId, Long chippingLocationId, LifeStatus lifeStatus,
                                           Gender gender, Integer from, Integer size);

    AnimalDto addAnimal(AnimalDto animalDto);

    AnimalDto updateAnimal(Long animalId, AnimalDto updatedAnimalDto);

    void deleteAnimalById(Long animalId);

    AnimalDto addTypeToAnimal(Long animalId, AnimalTypeDto animalTypeDto);

    AnimalDto updateTypeOfAnimal(Long animalId, AnimalTypeDto oldTypeDto, AnimalTypeDto newTypeDto);

    AnimalDto deleteTypeOfAnimal(Long animalId, AnimalTypeDto animalTypeDto);

}
