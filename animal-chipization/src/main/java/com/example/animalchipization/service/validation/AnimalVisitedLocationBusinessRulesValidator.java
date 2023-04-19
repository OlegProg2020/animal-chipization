package com.example.animalchipization.service.validation;

import com.example.animalchipization.entity.Animal;
import com.example.animalchipization.entity.AnimalVisitedLocation;
import com.example.animalchipization.entity.LocationPoint;

public interface AnimalVisitedLocationBusinessRulesValidator {

    void validateAnimalVisitedLocationIsRelatedToAnimal(AnimalVisitedLocation visitedLocation,
                                                        Animal animal);

    void validateNewAnimalVisitedLocation(AnimalVisitedLocation newVisitedLocation);

    void validatePatchAnimalVisitedLocation(AnimalVisitedLocation currentVisitedLocation,
                                            LocationPoint newLocation);

}
