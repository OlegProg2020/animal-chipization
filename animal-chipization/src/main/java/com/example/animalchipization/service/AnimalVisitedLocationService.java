package com.example.animalchipization.service;

import com.example.animalchipization.dto.AnimalVisitedLocationDto;

import java.time.ZonedDateTime;
import java.util.Collection;

public interface AnimalVisitedLocationService {

    AnimalVisitedLocationDto findById(Long id);

    Collection<AnimalVisitedLocationDto> searchForAnimalVisitedLocations(
            Long animalId, ZonedDateTime startDateTime,
            ZonedDateTime endDateTime, Integer from,
            Integer size);

    AnimalVisitedLocationDto update(Long animalId,
                                    Long visitedLocationPointId,
                                    Long locationPointId);

    void delete(Long animalId, Long visitedPointId);

    Collection<AnimalVisitedLocationDto> findAllById(Collection<Long> ids);

}
