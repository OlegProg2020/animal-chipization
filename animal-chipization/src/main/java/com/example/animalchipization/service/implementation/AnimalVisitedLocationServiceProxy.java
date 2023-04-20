package com.example.animalchipization.service.implementation;

import com.example.animalchipization.dto.AnimalVisitedLocationDto;
import com.example.animalchipization.entity.Animal;
import com.example.animalchipization.entity.AnimalVisitedLocation;
import com.example.animalchipization.entity.LocationPoint;
import com.example.animalchipization.service.AnimalVisitedLocationService;
import com.example.animalchipization.service.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;

@Component
@Qualifier("AnimalVisitedLocationServiceProxy")
public class AnimalVisitedLocationServiceProxy implements AnimalVisitedLocationService {

    private final AnimalVisitedLocationService animalVisitedLocationService;
    private final Mapper<AnimalVisitedLocation, AnimalVisitedLocationDto> animalVisitedLocationMapper;

    @Autowired
    public AnimalVisitedLocationServiceProxy(
            @Qualifier("AnimalVisitedLocationServiceImpl") AnimalVisitedLocationService
                    animalVisitedLocationService,
            Mapper<AnimalVisitedLocation, AnimalVisitedLocationDto> animalVisitedLocationMapper) {

        this.animalVisitedLocationService = animalVisitedLocationService;
        this.animalVisitedLocationMapper = animalVisitedLocationMapper;
    }

    @Override
    public AnimalVisitedLocationDto findById(Long id) {
        return animalVisitedLocationService.findById(id);
    }

    @Override
    public Collection<AnimalVisitedLocationDto> searchForAnimalVisitedLocations(
            Long animalId, ZonedDateTime startDateTime,
            ZonedDateTime endDateTime, Integer from,
            Integer size) {

        return animalVisitedLocationService.searchForAnimalVisitedLocations(animalId, startDateTime,
                endDateTime, from, size);
    }

    @Override
    public AnimalVisitedLocationDto save(Long animalId, Long pointId) {
        AnimalVisitedLocationDto visitedLocationDto = animalVisitedLocationService.save(animalId, pointId);

        AnimalVisitedLocation visitedLocation = animalVisitedLocationMapper.toEntity(visitedLocationDto);
        Animal animal = visitedLocation.getAnimal();

        LocationPoint latestLocation = visitedLocation.getLocationPoint();
        LocationPoint previousLocation = findThePreviousLocationWhereAnimalWasLocated(animal, visitedLocation);



        return visitedLocationDto;
    }

    @Override
    public AnimalVisitedLocationDto update(Long animalId, Long visitedLocationPointId, Long locationPointId) {
        return animalVisitedLocationService.update(animalId, visitedLocationPointId, locationPointId);
    }

    @Override
    public void delete(Long animalId, Long visitedPointId) {
        animalVisitedLocationService.delete(animalId, visitedPointId);
    }

    @Override
    public Collection<AnimalVisitedLocationDto> findAllById(Collection<Long> ids) {
        return animalVisitedLocationService.findAllById(ids);
    }



    private LocationPoint findThePreviousLocationWhereAnimalWasLocated(Animal animal,
                                                                    AnimalVisitedLocation visitedLocation) {

        List<AnimalVisitedLocation> visitedLocations = animal.getVisitedLocations();
        if (visitedLocations.size() <= 1) {
            return animal.getChippingLocation();
        } else {
            int previousVisitedLocationIndex = visitedLocations.indexOf(visitedLocation) - 1;
            return visitedLocations.get(previousVisitedLocationIndex).getLocationPoint();
        }
    }

}
