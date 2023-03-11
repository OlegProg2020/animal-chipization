package com.example.animalchipization.service;

import com.example.animalchipization.web.form.AnimalForm;
import com.example.animalchipization.model.Animal;

import static com.example.animalchipization.model.Animal.LifeStatus;
import static com.example.animalchipization.model.Animal.Gender;

import java.time.LocalDateTime;

public interface AnimalService {

    Animal findAnimalById(Long animalId);

    Iterable<Animal> searchForAnimals(LocalDateTime startDateTime, LocalDateTime endDateTime,
                                      Long chipperId, Long chippingLocationId, LifeStatus lifeStatus,
                                      Gender gender, Integer from, Integer size);

    Animal addAnimal(AnimalForm animalForm);
}
