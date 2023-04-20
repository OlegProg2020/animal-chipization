package com.example.animalchipization.service;

import com.example.animalchipization.dto.AnimalVisitedLocationDto;

public interface AnimalVisitedLocationSavingService {

    AnimalVisitedLocationDto save(Long animalId, Long pointId);

}
