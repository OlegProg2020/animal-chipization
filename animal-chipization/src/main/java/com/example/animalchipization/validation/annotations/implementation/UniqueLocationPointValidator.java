package com.example.animalchipization.validation.annotations.implementation;

import com.example.animalchipization.data.repository.LocationPointRepository;
import com.example.animalchipization.exception.LocationPointWithThisCoordinatesAlreadyExistsException;
import com.example.animalchipization.model.LocationPoint;
import com.example.animalchipization.validation.annotations.UniqueLocationPoint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UniqueLocationPointValidator implements ConstraintValidator<UniqueLocationPoint, LocationPoint> {

    private final LocationPointRepository locationPointRepository;

    @Autowired
    public UniqueLocationPointValidator(LocationPointRepository locationPointRepository) {
        this.locationPointRepository = locationPointRepository;
    }

    @Override
    public void initialize(UniqueLocationPoint uniqueLocationPoint) {
    }

    @Override
    public boolean isValid(LocationPoint locationPoint, ConstraintValidatorContext constraintValidatorContext) {
        if (!locationPointRepository.existsByLatitudeAndLongitude(locationPoint.getLatitude(),
                locationPoint.getLongitude())) {
            return true;
        } else {
            throw new LocationPointWithThisCoordinatesAlreadyExistsException();
        }
    }
}
