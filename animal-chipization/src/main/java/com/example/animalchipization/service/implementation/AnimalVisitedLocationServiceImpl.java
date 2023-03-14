package com.example.animalchipization.service.implementation;

import com.example.animalchipization.data.repository.AnimalRepository;
import com.example.animalchipization.data.repository.AnimalVisitedLocationRepository;
import com.example.animalchipization.data.repository.LocationPointRepository;
import com.example.animalchipization.exception.AnimalIsAlreadyAtThisPointException;
import com.example.animalchipization.exception.AttemptAddingLocationToAnimalWithDeadStatusException;
import com.example.animalchipization.exception.FirstLocationPointCoincidesWithChippingPointException;
import com.example.animalchipization.model.Animal;
import com.example.animalchipization.model.AnimalVisitedLocation;
import com.example.animalchipization.model.LocationPoint;
import com.example.animalchipization.model.enums.LifeStatus;
import com.example.animalchipization.service.AnimalVisitedLocationService;
import com.example.animalchipization.util.OffsetBasedPageRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import static com.example.animalchipization.data.specification.AnimalVisitedLocationSpecification.*;

@Service
public class AnimalVisitedLocationServiceImpl implements AnimalVisitedLocationService {

    private final AnimalRepository animalRepository;
    private final AnimalVisitedLocationRepository animalVisitedLocationRepository;
    private final LocationPointRepository locationPointRepository;

    @Autowired
    public AnimalVisitedLocationServiceImpl(AnimalRepository animalRepository, AnimalVisitedLocationRepository animalVisitedLocationRepository, LocationPointRepository locationPointRepository) {
        this.animalRepository = animalRepository;
        this.animalVisitedLocationRepository = animalVisitedLocationRepository;
        this.locationPointRepository = locationPointRepository;
    }

    @Override
    public Iterable<AnimalVisitedLocation> searchForAnimalVisitedLocations(Long animalId, LocalDateTime startDateTime, LocalDateTime endDateTime, Integer from, Integer size) {
        if (animalRepository.existsById(animalId)) {
            OffsetBasedPageRequest pageRequest = new OffsetBasedPageRequest(from, size, Sort.by("dateTimeOfVisitLocationPoint").ascending());
            Specification<AnimalVisitedLocation> specifications = Specification.where(hasAnimalId(animalId).and(hasDateTimeOfVisitLocationPointGreaterThanOrEqualTo(startDateTime)).and(hasDateTimeOfVisitLocationPointLessThanOrEqualTo(endDateTime)));
            return animalVisitedLocationRepository.findAll(specifications, pageRequest).getContent();
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public AnimalVisitedLocation addAnimalVisitedLocation(@Valid AnimalVisitedLocation animalVisitedLocation) {
        Animal animal = animalVisitedLocation.getAnimal();
        if (animal.getLifeStatus() == LifeStatus.DEAD) {
            throw new AttemptAddingLocationToAnimalWithDeadStatusException();
        }
        List<AnimalVisitedLocation> visitedLocations = animal.getVisitedLocations();
        if (visitedLocations.size() == 0 && animal.getChippingLocation().equals(animalVisitedLocation.getLocationPoint())) {
            throw new FirstLocationPointCoincidesWithChippingPointException();
        }
        if (visitedLocations.size() != 0
                && visitedLocations.get(visitedLocations.size() - 1).equals(animalVisitedLocation)) {
            throw new AnimalIsAlreadyAtThisPointException();
        }
        return animalVisitedLocationRepository.save(animalVisitedLocation);
    }

    @Override
    public AnimalVisitedLocation updateAnimalVisitedLocation(Long animalId,
                                                             Long visitedLocationPointId,
                                                             Long locationPointId) {
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(NoSuchElementException::new);
        AnimalVisitedLocation visitedLocationPoint = animalVisitedLocationRepository
                .findByAnimal_IdAndLocationPoint_Id(animalId, visitedLocationPointId)
                .orElseThrow(NoSuchElementException::new);
        LocationPoint locationPoint = locationPointRepository
                .findById(locationPointId).orElseThrow(NoSuchElementException::new);

        ListIterator<LocationPoint> pointsIterator = animal.getVisitedLocations()
                .stream().map(AnimalVisitedLocation::getLocationPoint).toList().listIterator();
        if (locationPoint.equals(pointsIterator.next())
                && locationPoint.equals(animal.getChippingLocation())) {
            throw new FirstLocationPointCoincidesWithChippingPointException();
        }
        if (visitedLocationPoint.getLocationPoint().equals(locationPoint)) {
            throw new AnimalIsAlreadyAtThisPointException();
        }
        pointsIterator.set(visitedLocationPoint.getLocationPoint());
        if (pointsIterator.hasNext() && pointsIterator.next().equals(locationPoint)) {
            throw new AnimalIsAlreadyAtThisPointException();
        }
        pointsIterator.set(visitedLocationPoint.getLocationPoint());
        if (pointsIterator.hasPrevious() && pointsIterator.previous().equals(locationPoint)) {
            throw new AnimalIsAlreadyAtThisPointException();
        }

        visitedLocationPoint.setLocationPoint(locationPoint);
        return animalVisitedLocationRepository.save(visitedLocationPoint);
    }

    @Override
    public void deleteAnimalVisitedLocation(Long animalId, Long pointId) {
        Animal animal = animalRepository.findById(animalId).orElseThrow(NoSuchElementException::new);
        AnimalVisitedLocation visitedLocationPoint = animalVisitedLocationRepository
                .findByAnimal_IdAndLocationPoint_Id(animalId, pointId).orElseThrow(NoSuchElementException::new);

        ListIterator<AnimalVisitedLocation> visitedLocationsIterator = animal.getVisitedLocations().listIterator();
        visitedLocationsIterator.set(visitedLocationPoint);
        if (visitedLocationsIterator.nextIndex() == 0 && visitedLocationsIterator.hasNext()
                && visitedLocationsIterator.next().getLocationPoint().equals(animal.getChippingLocation())) {
            animalVisitedLocationRepository.delete(visitedLocationsIterator.previous());
        }
        animalVisitedLocationRepository.delete(visitedLocationPoint);

    }

}
