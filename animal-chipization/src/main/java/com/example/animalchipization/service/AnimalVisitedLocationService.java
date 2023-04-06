package com.example.animalchipization.service;

import com.example.animalchipization.entity.AnimalVisitedLocation;
import com.example.animalchipization.web.dto.AnimalVisitedLocationDto;

import java.time.LocalDateTime;

public interface AnimalVisitedLocationService {

    Iterable<AnimalVisitedLocation> searchForAnimalVisitedLocations(Long animalId, LocalDateTime startDateTime,
                                                                    LocalDateTime endDateTime, Integer from,
                                                                    Integer size);

    AnimalVisitedLocation addAnimalVisitedLocation(AnimalVisitedLocation animalVisitedLocation);

    AnimalVisitedLocation updateAnimalVisitedLocation(Long animalId, Long visitedLocationPointId, Long locationPointId);

    void deleteAnimalVisitedLocation(Long animalId, Long pointId);

    AnimalVisitedLocationDto findById(Long id);
}
