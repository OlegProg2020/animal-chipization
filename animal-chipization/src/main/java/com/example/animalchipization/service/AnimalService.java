package com.example.animalchipization.service;

import com.example.animalchipization.dto.AnimalDto;
import com.example.animalchipization.entity.enums.Gender;
import com.example.animalchipization.entity.enums.LifeStatus;

import java.time.ZonedDateTime;
import java.util.Collection;

public interface AnimalService {

    AnimalDto findById(Long id);

    Collection<AnimalDto> searchForAnimals(ZonedDateTime startDateTime, ZonedDateTime endDateTime,
                                           Long chipperId, Long chippingLocationId, LifeStatus lifeStatus,
                                           Gender gender, Integer from, Integer size);

    AnimalDto save(AnimalDto animalDto);

    AnimalDto update(Long animalId, AnimalDto updatedAnimalDto);

    void deleteById(Long id);

    AnimalDto addTypeToAnimal(Long animalId, Long typeId);

    AnimalDto updateTypeOfAnimal(Long animalId, Long oldTypeId, Long newTypeId);

    AnimalDto deleteTypeOfAnimal(Long animalId, Long typeId);

}
