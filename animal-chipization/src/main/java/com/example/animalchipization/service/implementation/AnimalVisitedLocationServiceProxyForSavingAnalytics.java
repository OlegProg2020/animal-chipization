package com.example.animalchipization.service.implementation;

import com.example.animalchipization.data.repository.AreaAnalyticsRepository;
import com.example.animalchipization.data.repository.AreaRepository;
import com.example.animalchipization.dto.AnimalVisitedLocationDto;
import com.example.animalchipization.entity.*;
import com.example.animalchipization.entity.enums.StatusOfAnimalVisitToArea;
import com.example.animalchipization.service.AnimalVisitedLocationService;
import com.example.animalchipization.service.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
@Qualifier("AnimalVisitedLocationServiceProxyForSavingAnalytics")
public class AnimalVisitedLocationServiceProxyForSavingAnalytics implements AnimalVisitedLocationService {

    private final AnimalVisitedLocationService animalVisitedLocationService;
    private final Mapper<AnimalVisitedLocation, AnimalVisitedLocationDto> animalVisitedLocationMapper;
    private final AreaAnalyticsRepository analyticsRepository;
    private final AreaRepository areaRepository;

    @Autowired
    public AnimalVisitedLocationServiceProxyForSavingAnalytics(
            @Qualifier("AnimalVisitedLocationServiceImpl") AnimalVisitedLocationService
                    animalVisitedLocationService,
            Mapper<AnimalVisitedLocation, AnimalVisitedLocationDto> animalVisitedLocationMapper,
            AreaAnalyticsRepository analyticsRepository,
            AreaRepository areaRepository) {

        this.animalVisitedLocationService = animalVisitedLocationService;
        this.animalVisitedLocationMapper = animalVisitedLocationMapper;
        this.analyticsRepository = analyticsRepository;
        this.areaRepository = areaRepository;
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

        LocationPoint previousLocation = findThePreviousLocationWhereAnimalWasLocated(animal, visitedLocation);
        LocationPoint latestLocation = visitedLocation.getLocationPoint();
        Optional<Area> previousLocationArea = areaRepository.findAreaContainingLocationPoint(previousLocation);
        Optional<Area> latestLocationArea = areaRepository.findAreaContainingLocationPoint(latestLocation);

        if(previousLocationArea.isPresent()) {
            AreaAnalytics previousAnalytics = new AreaAnalytics();
            previousAnalytics.setArea(previousLocationArea.get());
            previousAnalytics.setAnimal(animal);
            previousAnalytics.setDateTime(visitedLocation.getDateTimeOfVisitLocationPoint().toLocalDate());
            previousAnalytics.setStatusOfVisit(StatusOfAnimalVisitToArea.GONE);
            analyticsRepository.save(previousAnalytics);
        }
        if(latestLocationArea.isPresent()) {
            AreaAnalytics latestAnalytics = new AreaAnalytics();
            latestAnalytics.setArea(latestLocationArea.get());
            latestAnalytics.setAnimal(animal);
            latestAnalytics.setDateTime(visitedLocation.getDateTimeOfVisitLocationPoint().toLocalDate());
            latestAnalytics.setStatusOfVisit(StatusOfAnimalVisitToArea.ARRIVED);
            analyticsRepository.save(latestAnalytics);
        }

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
