package com.example.animalchipization.service;

import com.example.animalchipization.model.AnimalVisitedLocation;

import java.time.LocalDateTime;

public interface AnimalVisitedLocationService {

    Iterable<AnimalVisitedLocation> searchForAnimalVisitedLocations(Long animalId, LocalDateTime startDateTime,
                                                                    LocalDateTime endDateTime, Integer from,
                                                                    Integer size);
    AnimalVisitedLocation addAnimalVisitedLocation(AnimalVisitedLocation animalVisitedLocation);
    AnimalVisitedLocation updateAnimalVisitedLocation(Long animalId, Long visitedLocationPointId, Long locationPointId);
}
