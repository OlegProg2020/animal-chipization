package com.example.animalchipization.service.validation.implementation;

import com.example.animalchipization.entity.Animal;
import com.example.animalchipization.entity.AnimalVisitedLocation;
import com.example.animalchipization.entity.LocationPoint;
import com.example.animalchipization.entity.enums.LifeStatus;
import com.example.animalchipization.exception.AnimalIsAlreadyAtThisPointException;
import com.example.animalchipization.exception.AttemptToRemoveAnimalNotAtTheChippingPointException;
import com.example.animalchipization.exception.SettingLifeStatusInAliveFromDeadException;
import com.example.animalchipization.service.validation.AnimalBusinessRulesValidator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AnimalBusinessRulesValidatorImpl implements AnimalBusinessRulesValidator {

    @Override
    public void validatePatchAnimal(Animal currentAnimal, Animal patchAnimal) {
        if (isAnimalChippingLocationEqualsFirstVisitedLocation(
                patchAnimal, currentAnimal.getVisitedLocations())) {
            throw new AnimalIsAlreadyAtThisPointException();
        }
        if (!isNewLifeStatusValid(currentAnimal.getLifeStatus(), patchAnimal.getLifeStatus())) {
            throw new SettingLifeStatusInAliveFromDeadException();
        }
    }

    @Override
    public void validateDeletingAnimal(Animal deletingAnimal) {
        if (!isAnimalAtChippingLocation(deletingAnimal)) {
            throw new AttemptToRemoveAnimalNotAtTheChippingPointException();
        }
    }

    private boolean isAnimalChippingLocationEqualsFirstVisitedLocation(
            Animal animal,
            List<AnimalVisitedLocation> visitedLocations) {

        LocationPoint chippingLocation = animal.getChippingLocation();

        if (visitedLocations.size() > 0) {
            return visitedLocations.get(0).getLocationPoint()
                    .equals(chippingLocation);
        }
        return false;
    }

    private boolean isNewLifeStatusValid(LifeStatus currentStatus, LifeStatus newStatus) {
        if (currentStatus == LifeStatus.DEAD && newStatus == LifeStatus.ALIVE) {
            return false;
        }
        return true;
    }

    private boolean isAnimalAtChippingLocation(Animal animal) {
        List<AnimalVisitedLocation> visitedLocations = animal.getVisitedLocations();

        int lastVisitedLocationIndex = visitedLocations.size() - 1;
        if (lastVisitedLocationIndex >= 0) {
            AnimalVisitedLocation lastVisitedLocation = visitedLocations.get(lastVisitedLocationIndex);
            return animal.getChippingLocation().equals(lastVisitedLocation.getLocationPoint());
        }
        return true;
    }

}
