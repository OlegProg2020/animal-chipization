package com.example.animalchipization.service.implementation;

import com.example.animalchipization.data.repository.AnimalRepository;
import com.example.animalchipization.data.repository.AnimalVisitedLocationRepository;
import com.example.animalchipization.data.repository.LocationPointRepository;
import com.example.animalchipization.entity.Animal;
import com.example.animalchipization.entity.AnimalVisitedLocation;
import com.example.animalchipization.entity.LocationPoint;
import com.example.animalchipization.entity.enums.LifeStatus;
import com.example.animalchipization.exception.AnimalIsAlreadyAtThisPointException;
import com.example.animalchipization.exception.AttemptAddingLocationToAnimalWithDeadStatusException;
import com.example.animalchipization.exception.FirstLocationPointConcidesWithChippingPointException;
import com.example.animalchipization.mapper.Mapper;
import com.example.animalchipization.service.AnimalVisitedLocationService;
import com.example.animalchipization.util.OffsetBasedPageRequest;
import com.example.animalchipization.web.dto.AnimalVisitedLocationDto;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.example.animalchipization.data.specification.AnimalVisitedLocationSpecification.*;

@Service
public class AnimalVisitedLocationServiceImpl implements AnimalVisitedLocationService {

    private final AnimalRepository animalRepository;
    private final AnimalVisitedLocationRepository animalVisitedLocationRepository;
    private final LocationPointRepository locationPointRepository;
    private final Mapper<AnimalVisitedLocation, AnimalVisitedLocationDto> animalVisitedLocationMapper;

    @Autowired
    public AnimalVisitedLocationServiceImpl(
            AnimalRepository animalRepository,
            AnimalVisitedLocationRepository animalVisitedLocationRepository,
            LocationPointRepository locationPointRepository,
            Mapper<AnimalVisitedLocation, AnimalVisitedLocationDto> animalVisitedLocationMapper) {

        this.animalRepository = animalRepository;
        this.animalVisitedLocationRepository = animalVisitedLocationRepository;
        this.locationPointRepository = locationPointRepository;
        this.animalVisitedLocationMapper = animalVisitedLocationMapper;
    }

    @Override
    public AnimalVisitedLocationDto findById(@Min(1) Long id) {
        return animalVisitedLocationMapper.toDto(animalVisitedLocationRepository.findById(id)
                .orElseThrow(NoSuchElementException::new));
    }

    @Override
    public Collection<AnimalVisitedLocationDto> searchForAnimalVisitedLocations(
            @Min(1) Long animalId,
            LocalDateTime startDateTime,
            LocalDateTime endDateTime,
            @Min(0) Integer from,
            @Min(1) Integer size) {

        if (animalRepository.existsById(animalId)) {
            OffsetBasedPageRequest pageRequest = new OffsetBasedPageRequest(from, size,
                    Sort.by("dateTimeOfVisitLocationPoint").ascending());

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
    public AnimalVisitedLocationDto addAnimalVisitedLocation(@Min(1) Long animalId, @Min(1) Long pointId) {

        Animal animal = animalRepository.findById(animalId).orElseThrow(NoSuchElementException::new);
        LocationPoint point = locationPointRepository.findById(pointId).orElseThrow(NoSuchElementException::new);

        AnimalVisitedLocation visitedLocation = new AnimalVisitedLocation();
        visitedLocation.setAnimal(animal);
        visitedLocation.setLocationPoint(point);

        this.validateNewAnimalVisitedLocation(visitedLocation);

        return animalVisitedLocationMapper.toDto(animalVisitedLocationRepository.save(visitedLocation));
    }

    @Override
    @Transactional
    public AnimalVisitedLocationDto updateAnimalVisitedLocation(@Min(1) Long animalId,
                                                                @Min(1) Long visitedLocationPointId,
                                                                @Min(1) Long locationPointId) {

        Animal animal = animalRepository.findById(animalId).orElseThrow(NoSuchElementException::new);
        AnimalVisitedLocation visitedLocation = animalVisitedLocationRepository
                .findById(visitedLocationPointId).orElseThrow(NoSuchElementException::new);
        LocationPoint locationPoint = locationPointRepository
                .findById(locationPointId).orElseThrow(NoSuchElementException::new);

        this.checkAnimalVisitedLocationBelongsToAnimal(visitedLocation, animal);
        this.validatePatchAnimalVisitedLocation(visitedLocation, locationPoint);

        return animalVisitedLocationMapper.toDto(animalVisitedLocationRepository.save(visitedLocation));
    }

    @Override
    public void deleteAnimalVisitedLocation(Long animalId, Long visitedPointId) {
        Animal animal = animalRepository.findById(animalId).orElseThrow(NoSuchElementException::new);
        AnimalVisitedLocation visitedLocationPoint = animalVisitedLocationRepository.findById(visitedPointId)
                .orElseThrow(NoSuchElementException::new);

        this.checkAnimalVisitedLocationBelongsToAnimal(visitedLocationPoint, animal);

        if (this.isSecondVisitedLocationEqualsAnimalChippingLocation(animal)) {
            animalVisitedLocationRepository.delete(animal.getVisitedLocations().get(1));
        }
        animalVisitedLocationRepository.delete(visitedLocationPoint);
    }



    /* helper method */

    private void checkAnimalVisitedLocationBelongsToAnimal(AnimalVisitedLocation animalVisitedLocation,
                                                           Animal animal) {
        if (!animalVisitedLocation.getAnimal().getId().equals(animal.getId())) {
            throw new NoSuchElementException();
        }
    }

    /* helper methods for addAnimalVisitedLocation(...) */

    private boolean isAnimalHasNotVisitedLocations(Animal animal) {
        return animal.getVisitedLocations().size() == 0;
    }

    private boolean isLocationEqualsAnimalChippingLocation(LocationPoint locationPoint, Animal animal) {
        return locationPoint.equals(animal.getChippingLocation());
    }

    private boolean isLocationEqualsAnimalLastVisitedLocation(LocationPoint locationPoint, Animal animal) {
        AnimalVisitedLocation lastVisitedLocation = animal.getVisitedLocations().peekLast();
        if (lastVisitedLocation != null) {
            return locationPoint.equals(lastVisitedLocation.getLocationPoint());
        }
        return false;
    }

    private void validateNewAnimalVisitedLocation(AnimalVisitedLocation visitedLocation) {
        Animal animal = visitedLocation.getAnimal();
        LocationPoint locationPoint = visitedLocation.getLocationPoint();

        if (animal.getLifeStatus() == LifeStatus.DEAD) {
            throw new AttemptAddingLocationToAnimalWithDeadStatusException();
        }
        if (this.isAnimalHasNotVisitedLocations(animal)
                && this.isLocationEqualsAnimalChippingLocation(locationPoint, animal)) {
            throw new FirstLocationPointConcidesWithChippingPointException();
        }
        if (this.isLocationEqualsAnimalLastVisitedLocation(locationPoint, animal)) {
            throw new AnimalIsAlreadyAtThisPointException();
        }
    }

    /* helper methods for updateAnimalVisitedLocation(...) */

    private boolean isFirstVisitedLocationOfAnimal(LocationPoint locationPoint, Animal animal) {
        AnimalVisitedLocation firstVisitedLocation = animal.getVisitedLocations().peekFirst();
        if (firstVisitedLocation != null) {
            return locationPoint.equals(firstVisitedLocation.getLocationPoint());
        }
        return false;
    }

    private boolean isLocationEqualsNextAnimalVisitedLocationPoint(AnimalVisitedLocation visitedLocation,
                                                                   Animal animal) {

        List<AnimalVisitedLocation> visitedLocations = animal.getVisitedLocations();

        int visitedLocationIndex = visitedLocations.indexOf(visitedLocation);
        int nextVisitedLocationIndex = visitedLocationIndex + 1;
        if (visitedLocationIndex > -1 && (visitedLocations.size() - nextVisitedLocationIndex > 0)) {
            return visitedLocation.getLocationPoint()
                    .equals(visitedLocations.get(nextVisitedLocationIndex).getLocationPoint());
        }
        return false;
    }

    private boolean isLocationEqualsPreviousAnimalVisitedLocationPoint(AnimalVisitedLocation visitedLocation,
                                                                       Animal animal) {

        List<AnimalVisitedLocation> visitedLocations = animal.getVisitedLocations();

        int visitedLocationIndex = visitedLocations.indexOf(visitedLocation);
        int previousVisitedLocationIndex = visitedLocationIndex - 1;
        if (visitedLocationIndex > -1 && previousVisitedLocationIndex >= 0) {
            return visitedLocation.getLocationPoint()
                    .equals(visitedLocations.get(previousVisitedLocationIndex).getLocationPoint());
        }
        return false;
    }

    public void validatePatchAnimalVisitedLocation(AnimalVisitedLocation visitedLocation,
                                                   LocationPoint newLocation) {
        Animal animal = visitedLocation.getAnimal();

        if (this.isFirstVisitedLocationOfAnimal(newLocation, animal) &&
                this.isLocationEqualsAnimalChippingLocation(newLocation, animal)) {
            throw new FirstLocationPointConcidesWithChippingPointException();
        }
        /* updating point to the same point */
        if (visitedLocation.getLocationPoint().equals(newLocation)) {
            throw new AnimalIsAlreadyAtThisPointException();
        }
        if (this.isLocationEqualsNextAnimalVisitedLocationPoint(visitedLocation, animal) ||
                this.isLocationEqualsPreviousAnimalVisitedLocationPoint(visitedLocation, animal)) {
            throw new AnimalIsAlreadyAtThisPointException();
        }
    }

    /* helper methods for deleteAnimalVisitedLocation(...) */

    public boolean isSecondVisitedLocationEqualsAnimalChippingLocation(Animal animal) {
        List<AnimalVisitedLocation> visitedLocations = animal.getVisitedLocations();

        return (visitedLocations.size() >= 2
                && visitedLocations.get(1).getLocationPoint().equals(animal.getChippingLocation()));
    }

}
