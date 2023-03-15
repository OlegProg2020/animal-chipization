package com.example.animalchipization.service;

import com.example.animalchipization.model.Animal;
import com.example.animalchipization.model.enums.Gender;
import com.example.animalchipization.model.enums.LifeStatus;

import java.time.LocalDateTime;

public interface AnimalService {

    Animal findAnimalById(Long animalId);

    Iterable<Animal> searchForAnimals(LocalDateTime startDateTime, LocalDateTime endDateTime,
                                      Long chipperId, Long chippingLocationId, LifeStatus lifeStatus,
                                      Gender gender, Integer from, Integer size);

    Animal addAnimal(Animal animal);

    Animal addTypeToAnimal(Long animalId, Long typeId);

    Animal updateTypeOfAnimal(Long animalId, Long oldTypeId, Long newTypeId);

    Animal deleteTypeOfAnimal(Long animalId, Long typeId);
    Animal updateAnimal(Long animalId, Animal newAnimalDetails);
    void deleteAnimalById(Long animalId);
}
