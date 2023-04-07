package com.example.animalchipization.service;

import com.example.animalchipization.entity.Animal;
import com.example.animalchipization.entity.enums.Gender;
import com.example.animalchipization.entity.enums.LifeStatus;
import com.example.animalchipization.web.dto.AnimalDto;

import java.time.ZonedDateTime;
import java.util.Collection;

public interface AnimalService {

    AnimalDto findById(Long animalId);

    Collection<AnimalDto> searchForAnimals(ZonedDateTime startDateTime, ZonedDateTime endDateTime,
                                           Long chipperId, Long chippingLocationId, LifeStatus lifeStatus,
                                           Gender gender, Integer from, Integer size);

    AnimalDto addAnimal(AnimalDto animalDto);

    Animal updateAnimal(Long animalId, Animal newAnimalDetails);

    void deleteAnimalById(Long animalId);

    Animal addTypeToAnimal(Long animalId, Long typeId);

    Animal updateTypeOfAnimal(Long animalId, Long oldTypeId, Long newTypeId);

    Animal deleteTypeOfAnimal(Long animalId, Long typeId);

}
