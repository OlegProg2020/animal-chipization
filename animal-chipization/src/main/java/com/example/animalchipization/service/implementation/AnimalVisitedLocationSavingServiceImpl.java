package com.example.animalchipization.service.implementation;

import com.example.animalchipization.data.repository.AnimalRepository;
import com.example.animalchipization.data.repository.AnimalVisitedLocationRepository;
import com.example.animalchipization.data.repository.LocationPointRepository;
import com.example.animalchipization.dto.AnimalVisitedLocationDto;
import com.example.animalchipization.entity.Animal;
import com.example.animalchipization.entity.AnimalVisitedLocation;
import com.example.animalchipization.entity.LocationPoint;
import com.example.animalchipization.service.AnimalVisitedLocationSavingService;
import com.example.animalchipization.service.mapper.Mapper;
import com.example.animalchipization.service.validation.AnimalVisitedLocationBusinessRulesValidator;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Qualifier("AnimalVisitedLocationSavingServiceImpl")
public class AnimalVisitedLocationSavingServiceImpl implements AnimalVisitedLocationSavingService {

    private final AnimalRepository animalRepository;
    private final LocationPointRepository locationPointRepository;
    private final AnimalVisitedLocationBusinessRulesValidator visitedLocationBusinessRulesValidator;
    private final Mapper<AnimalVisitedLocation, AnimalVisitedLocationDto> animalVisitedLocationMapper;
    private final AnimalVisitedLocationRepository animalVisitedLocationRepository;

    @Autowired
    public AnimalVisitedLocationSavingServiceImpl(
            AnimalRepository animalRepository,
            LocationPointRepository locationPointRepository,
            AnimalVisitedLocationBusinessRulesValidator visitedLocationBusinessRulesValidator,
            Mapper<AnimalVisitedLocation, AnimalVisitedLocationDto> animalVisitedLocationMapper,
            AnimalVisitedLocationRepository animalVisitedLocationRepository) {

        this.animalRepository = animalRepository;
        this.locationPointRepository = locationPointRepository;
        this.visitedLocationBusinessRulesValidator = visitedLocationBusinessRulesValidator;
        this.animalVisitedLocationMapper = animalVisitedLocationMapper;
        this.animalVisitedLocationRepository = animalVisitedLocationRepository;
    }

    @Override
    @Transactional
    public AnimalVisitedLocationDto save(@Min(1) Long animalId, @Min(1) Long pointId) {
        Animal animal = animalRepository.findById(animalId).orElseThrow(NoSuchElementException::new);
        LocationPoint newLocation = locationPointRepository.findById(pointId)
                .orElseThrow(NoSuchElementException::new);

        AnimalVisitedLocation newVisitedLocation = new AnimalVisitedLocation();
        newVisitedLocation.setAnimal(animal);
        newVisitedLocation.setLocationPoint(newLocation);

        visitedLocationBusinessRulesValidator.validateNewAnimalVisitedLocation(newVisitedLocation);

        return animalVisitedLocationMapper.toDto(animalVisitedLocationRepository.save(newVisitedLocation));
    }

}
