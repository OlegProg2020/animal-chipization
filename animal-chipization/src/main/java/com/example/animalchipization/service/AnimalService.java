package com.example.animalchipization.service;

import com.example.animalchipization.web.form.AnimalForm;
import com.example.animalchipization.model.Animal;
import com.example.animalchipization.model.enums.LifeStatus;
import com.example.animalchipization.model.enums.Gender;

import java.time.LocalDateTime;

public interface AnimalService {

    Animal findAnimalById(Long animalId);

    Iterable<Animal> searchForAnimals(LocalDateTime startDateTime, LocalDateTime endDateTime,
                                      Long chipperId, Long chippingLocationId, LifeStatus lifeStatus,
                                      Gender gender, Integer from, Integer size);

    Animal addAnimal(AnimalForm animalForm);
}