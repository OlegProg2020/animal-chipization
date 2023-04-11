package com.example.animalchipization.service;

import com.example.animalchipization.web.dto.AnimalVisitedLocationDto;

import java.time.ZonedDateTime;
import java.util.Collection;

public interface AnimalVisitedLocationService {

    AnimalVisitedLocationDto findById(Long pointId);

    Collection<AnimalVisitedLocationDto> searchForAnimalVisitedLocations(
            Long animalId, ZonedDateTime startDateTime,
            ZonedDateTime endDateTime, Integer from,
            Integer size);

    AnimalVisitedLocationDto addAnimalVisitedLocation(Long animalId, Long pointId);

    AnimalVisitedLocationDto updateAnimalVisitedLocation(Long animalId,
                                                         Long visitedLocationPointId,
                                                         Long locationPointId);

    void deleteAnimalVisitedLocation(Long animalId, Long visitedPointId);

}
