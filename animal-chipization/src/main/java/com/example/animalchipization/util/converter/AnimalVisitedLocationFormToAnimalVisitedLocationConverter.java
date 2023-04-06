package com.example.animalchipization.util.converter;

import com.example.animalchipization.data.repository.AnimalRepository;
import com.example.animalchipization.data.repository.LocationPointRepository;
import com.example.animalchipization.entity.Animal;
import com.example.animalchipization.entity.AnimalVisitedLocation;
import com.example.animalchipization.entity.LocationPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class AnimalVisitedLocationFormToAnimalVisitedLocationConverter
        implements Converter<AnimalVisitedLocationForm, AnimalVisitedLocation> {

    private final AnimalRepository animalRepository;
    private final LocationPointRepository locationPointRepository;

    @Autowired
    public AnimalVisitedLocationFormToAnimalVisitedLocationConverter(AnimalRepository animalRepository,
                                                                     LocationPointRepository locationPointRepository) {
        this.animalRepository = animalRepository;
        this.locationPointRepository = locationPointRepository;
    }

    @Override
    public AnimalVisitedLocation convert(AnimalVisitedLocationForm animalVisitedLocationForm) {
        Animal animal = animalRepository.findById(animalVisitedLocationForm.getAnimalId())
                .orElseThrow(NoSuchElementException::new);
        LocationPoint locationPoint = locationPointRepository
                .findById(animalVisitedLocationForm.getPointId())
                .orElseThrow(NoSuchElementException::new);
        return new AnimalVisitedLocation(animal, locationPoint);
    }

}

