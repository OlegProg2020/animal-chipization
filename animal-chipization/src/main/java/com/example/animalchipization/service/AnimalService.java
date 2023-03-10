package com.example.animalchipization.service;

import com.example.animalchipization.model.Animal;

import java.time.LocalDateTime;

public interface AnimalService {

    Animal findAnimalById(Long animalId);

    Iterable<Animal> searchForAnimals(LocalDateTime startDateTime, LocalDateTime endDateTime,
                                      Long chipperId, Long chippingLocationId, String lifeStatus,
                                      String gender, Integer from, Integer size);

}
