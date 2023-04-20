package com.example.animalchipization.service.implementation;

import com.example.animalchipization.data.repository.AnimalRepository;
import com.example.animalchipization.data.repository.AnimalVisitedLocationRepository;
import com.example.animalchipization.data.repository.LocationPointRepository;
import com.example.animalchipization.dto.AnimalVisitedLocationDto;
import com.example.animalchipization.entity.Animal;
import com.example.animalchipization.entity.AnimalVisitedLocation;
import com.example.animalchipization.entity.LocationPoint;
import com.example.animalchipization.service.AnimalVisitedLocationService;
import com.example.animalchipization.service.mapper.Mapper;
import com.example.animalchipization.service.validation.AnimalVisitedLocationBusinessRulesValidator;
import com.example.animalchipization.util.pagination.OffsetBasedPageRequest;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.example.animalchipization.data.specification.AnimalVisitedLocationSpecificationFactory.*;

@Service
@Qualifier("AnimalVisitedLocationServiceImpl")
public class AnimalVisitedLocationServiceImpl implements AnimalVisitedLocationService {

    private final AnimalRepository animalRepository;
    private final AnimalVisitedLocationRepository animalVisitedLocationRepository;
    private final LocationPointRepository locationPointRepository;
    private final Mapper<AnimalVisitedLocation, AnimalVisitedLocationDto> animalVisitedLocationMapper;
    private final AnimalVisitedLocationBusinessRulesValidator visitedLocationBusinessRulesValidator;

    @Autowired
    public AnimalVisitedLocationServiceImpl(
            AnimalRepository animalRepository,
            AnimalVisitedLocationRepository animalVisitedLocationRepository,
            LocationPointRepository locationPointRepository,
            Mapper<AnimalVisitedLocation, AnimalVisitedLocationDto> animalVisitedLocationMapper,
            AnimalVisitedLocationBusinessRulesValidator visitedLocationBusinessRulesValidator) {

        this.animalRepository = animalRepository;
        this.animalVisitedLocationRepository = animalVisitedLocationRepository;
        this.locationPointRepository = locationPointRepository;
        this.animalVisitedLocationMapper = animalVisitedLocationMapper;
        this.visitedLocationBusinessRulesValidator = visitedLocationBusinessRulesValidator;
    }

    @Override
    public AnimalVisitedLocationDto findById(@Min(1) Long id) {
        return animalVisitedLocationMapper.toDto(animalVisitedLocationRepository.findById(id)
                .orElseThrow(NoSuchElementException::new));
    }

    @Override
    public Collection<AnimalVisitedLocationDto> findAllById(Collection<@Min(1) Long> ids) {
        Collection<AnimalVisitedLocation> visitedLocations = animalVisitedLocationRepository
                .findAllByIdIn(ids);

        if (visitedLocations.size() < ids.size()) {
            throw new NoSuchElementException();
        }

        return visitedLocations.stream()
                .map(animalVisitedLocationMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Collection<AnimalVisitedLocationDto> searchForAnimalVisitedLocations(
            @Min(1) Long animalId,
            ZonedDateTime startDateTime,
            ZonedDateTime endDateTime,
            @Min(0) Integer from,
            @Min(1) Integer size) {

        if (animalRepository.existsById(animalId)) {
            OffsetBasedPageRequest pageRequest = new OffsetBasedPageRequest(from, size,
                    Sort.by("dateTimeOfVisitLocationPoint").ascending()
                            .and(Sort.by("id").ascending()));

            Specification<AnimalVisitedLocation> specifications = Specification.where(
                    hasAnimalId(animalId)
                            .and(hasDateTimeOfVisitLocationPointGreaterThanOrEqualTo(startDateTime))
                            .and(hasDateTimeOfVisitLocationPointLessThanOrEqualTo(endDateTime)));

            return animalVisitedLocationRepository.findAll(specifications, pageRequest).getContent()
                    .stream().map(animalVisitedLocationMapper::toDto)
                    .collect(Collectors.toList());
        } else {
            throw new NoSuchElementException();
        }
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

    @Override
    @Transactional
    public AnimalVisitedLocationDto update(@Min(1) Long animalId,
                                           @Min(1) Long visitedLocationPointId,
                                           @Min(1) Long locationPointId) {

        Animal animal = animalRepository.findById(animalId).orElseThrow(NoSuchElementException::new);
        AnimalVisitedLocation currentVisitedLocation = animalVisitedLocationRepository
                .findById(visitedLocationPointId).orElseThrow(NoSuchElementException::new);
        LocationPoint newLocation = locationPointRepository
                .findById(locationPointId).orElseThrow(NoSuchElementException::new);

        visitedLocationBusinessRulesValidator.validateAnimalVisitedLocationIsRelatedToAnimal(
                currentVisitedLocation, animal);
        visitedLocationBusinessRulesValidator.validatePatchAnimalVisitedLocation(
                currentVisitedLocation, newLocation);

        currentVisitedLocation.setLocationPoint(newLocation);
        return animalVisitedLocationMapper.toDto(animalVisitedLocationRepository.save(currentVisitedLocation));
    }

    @Override
    @Transactional
    public void delete(@Min(1) Long animalId, @Min(1) Long visitedPointId) {
        Animal animal = animalRepository.findById(animalId).orElseThrow(NoSuchElementException::new);
        AnimalVisitedLocation visitedLocationPoint = animalVisitedLocationRepository.findById(visitedPointId)
                .orElseThrow(NoSuchElementException::new);

        visitedLocationBusinessRulesValidator.validateAnimalVisitedLocationIsRelatedToAnimal(
                visitedLocationPoint, animal);

        if (isSecondVisitedLocationEqualsAnimalChippingLocation(animal)) {
            animalVisitedLocationRepository.delete(animal.getVisitedLocations().get(1));
        }
        animalVisitedLocationRepository.delete(visitedLocationPoint);
    }

    public boolean isSecondVisitedLocationEqualsAnimalChippingLocation(Animal animal) {
        List<AnimalVisitedLocation> visitedLocations = animal.getVisitedLocations();

        return (visitedLocations.size() >= 2
                && visitedLocations.get(1).getLocationPoint().equals(animal.getChippingLocation()));
    }

}
