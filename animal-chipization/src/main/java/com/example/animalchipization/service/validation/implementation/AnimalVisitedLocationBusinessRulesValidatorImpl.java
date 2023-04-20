package com.example.animalchipization.service.validation.implementation;

import com.example.animalchipization.entity.Animal;
import com.example.animalchipization.entity.AnimalVisitedLocation;
import com.example.animalchipization.entity.LocationPoint;
import com.example.animalchipization.entity.enums.LifeStatus;
import com.example.animalchipization.exception.AnimalIsAlreadyAtThisPointException;
import com.example.animalchipization.exception.AttemptAddingLocationToAnimalWithDeadStatusException;
import com.example.animalchipization.exception.FirstLocationPointConcidesWithChippingPointException;
import com.example.animalchipization.exception.UpdatingPointToSamePointException;
import com.example.animalchipization.service.validation.AnimalVisitedLocationBusinessRulesValidator;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;

@Component
public class AnimalVisitedLocationBusinessRulesValidatorImpl
        implements AnimalVisitedLocationBusinessRulesValidator {

    @Override
    public void validateAnimalVisitedLocationIsRelatedToAnimal(
            AnimalVisitedLocation visitedLocation,
            Animal animal) {

        if (!visitedLocation.getAnimal().getId().equals(animal.getId())) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public void validateNewAnimalVisitedLocation(AnimalVisitedLocation newVisitedLocation) {
        Animal animal = newVisitedLocation.getAnimal();
        LocationPoint location = newVisitedLocation.getLocationPoint();

        if (animal.getLifeStatus() == LifeStatus.DEAD) {
            throw new AttemptAddingLocationToAnimalWithDeadStatusException();
        }
        if (this.isAnimalHasNotVisitedLocations(animal)
                && this.isLocationEqualsAnimalChippingLocation(location, animal)) {
            throw new FirstLocationPointConcidesWithChippingPointException();
        }
        if (this.isLocationEqualsAnimalLastVisitedLocation(location, animal)) {
            throw new AnimalIsAlreadyAtThisPointException();
        }
    }

    @Override
    public void validatePatchAnimalVisitedLocation(AnimalVisitedLocation currentVisitedLocation,
                                                   LocationPoint newLocation) {

        Animal animal = currentVisitedLocation.getAnimal();

        if (isFirstVisitedLocationOfAnimal(newLocation, animal) &&
                isLocationEqualsAnimalChippingLocation(newLocation, animal)) {
            throw new FirstLocationPointConcidesWithChippingPointException();
        }
        if (currentVisitedLocation.getLocationPoint().equals(newLocation)) {
            throw new UpdatingPointToSamePointException();
        }
        if (isLocationEqualsNextAnimalVisitedLocation(newLocation, currentVisitedLocation, animal) ||
                isLocationEqualsPreviousAnimalVisitedLocation(newLocation, currentVisitedLocation, animal)) {
            throw new AnimalIsAlreadyAtThisPointException();
        }
    }

    private boolean isAnimalHasNotVisitedLocations(Animal animal) {
        return animal.getVisitedLocations().size() == 0;
    }

    private boolean isLocationEqualsAnimalChippingLocation(LocationPoint location, Animal animal) {
        return location.equals(animal.getChippingLocation());
    }

    private boolean isLocationEqualsAnimalLastVisitedLocation(LocationPoint location, Animal animal) {
        List<AnimalVisitedLocation> visitedLocations = animal.getVisitedLocations();

        int lastVisitedLocationIndex = visitedLocations.size() - 1;
        if (lastVisitedLocationIndex >= 0) {
            AnimalVisitedLocation lastVisitedLocation = visitedLocations.get(lastVisitedLocationIndex);
            return location.equals(lastVisitedLocation.getLocationPoint());
        }
        return false;
    }

    private boolean isFirstVisitedLocationOfAnimal(LocationPoint location, Animal animal) {
        List<AnimalVisitedLocation> visitedLocations = animal.getVisitedLocations();

        if (visitedLocations.size() > 0) {
            AnimalVisitedLocation firstVisitedLocation = visitedLocations.get(0);
            return location.equals(firstVisitedLocation.getLocationPoint());
        }
        return false;
    }

    private boolean isLocationEqualsNextAnimalVisitedLocation(LocationPoint location,
                                                              AnimalVisitedLocation currentVisitedLocation,
                                                              Animal animal) {

        List<AnimalVisitedLocation> visitedLocations = animal.getVisitedLocations();

        int currentVisitedLocationIndex = visitedLocations.indexOf(currentVisitedLocation);
        int nextVisitedLocationIndex = currentVisitedLocationIndex + 1;
        if ((currentVisitedLocationIndex > -1) && (nextVisitedLocationIndex < visitedLocations.size())) {
            return location.equals(visitedLocations.get(nextVisitedLocationIndex).getLocationPoint());
        }
        return false;
    }

    private boolean isLocationEqualsPreviousAnimalVisitedLocation(LocationPoint location,
                                                                  AnimalVisitedLocation currentVisitedLocation,
                                                                  Animal animal) {

        List<AnimalVisitedLocation> visitedLocations = animal.getVisitedLocations();

        int visitedLocationIndex = visitedLocations.indexOf(currentVisitedLocation);
        int previousVisitedLocationIndex = visitedLocationIndex - 1;
        if (previousVisitedLocationIndex >= 0) {
            return location.equals(visitedLocations.get(previousVisitedLocationIndex).getLocationPoint());
        }
        return false;
    }

}
