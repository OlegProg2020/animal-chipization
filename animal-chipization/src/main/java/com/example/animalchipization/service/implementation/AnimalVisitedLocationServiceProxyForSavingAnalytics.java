package com.example.animalchipization.service.implementation;

import com.example.animalchipization.data.repository.AreaRepository;
import com.example.animalchipization.dto.AnimalVisitedLocationDto;
import com.example.animalchipization.entity.*;
import com.example.animalchipization.entity.enums.StatusOfAnimalVisitToArea;
import com.example.animalchipization.service.AnimalVisitedLocationSavingService;
import com.example.animalchipization.service.AreaAnalyticsService;
import com.example.animalchipization.service.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Qualifier("AnimalVisitedLocationSavingServiceProxyForSavingAnalytics")
public class AnimalVisitedLocationServiceProxyForSavingAnalytics implements AnimalVisitedLocationSavingService {

    private final AnimalVisitedLocationSavingService animalVisitedLocationService;
    private final Mapper<AnimalVisitedLocation, AnimalVisitedLocationDto> animalVisitedLocationMapper;
    private final AreaAnalyticsService analyticsService;
    private final AreaRepository areaRepository;

    @Autowired
    public AnimalVisitedLocationServiceProxyForSavingAnalytics(
            @Qualifier("AnimalVisitedLocationSavingServiceImpl") AnimalVisitedLocationSavingService
                    animalVisitedLocationService,
            Mapper<AnimalVisitedLocation, AnimalVisitedLocationDto> animalVisitedLocationMapper,
            AreaAnalyticsService analyticsService,
            AreaRepository areaRepository) {

        this.animalVisitedLocationService = animalVisitedLocationService;
        this.animalVisitedLocationMapper = animalVisitedLocationMapper;
        this.analyticsService = analyticsService;
        this.areaRepository = areaRepository;
    }

    @Override
    public AnimalVisitedLocationDto save(Long animalId, Long pointId) {
        AnimalVisitedLocationDto visitedLocationDto = animalVisitedLocationService.save(animalId, pointId);

        AnimalVisitedLocation visitedLocation = animalVisitedLocationMapper.toEntity(visitedLocationDto);
        Animal animal = visitedLocation.getAnimal();

        LocationPoint previousLocation = findThePreviousLocationWhereAnimalWasLocated(animal, visitedLocation);
        LocationPoint latestLocation = visitedLocation.getLocationPoint();
        List<Area> previousLocationAreas = areaRepository.findAreasContainingLocationPoint(previousLocation);
        List<Area> latestLocationAreas = areaRepository.findAreasContainingLocationPoint(latestLocation);

        previousLocationAreas.removeAll(latestLocationAreas);
        latestLocationAreas.removeAll(previousLocationAreas);

        for (Area area : previousLocationAreas) {
            AreaAnalytics previousAnalytics = new AreaAnalytics();
            previousAnalytics.setArea(area);
            previousAnalytics.setAnimal(animal);
            previousAnalytics.setDate(visitedLocation.getDateTimeOfVisitLocationPoint().toLocalDate());
            previousAnalytics.setStatusOfVisit(StatusOfAnimalVisitToArea.GONE);
            analyticsService.save(previousAnalytics);
        }
        for (Area area : latestLocationAreas) {
            AreaAnalytics latestAnalytics = new AreaAnalytics();
            latestAnalytics.setArea(area);
            latestAnalytics.setAnimal(animal);
            latestAnalytics.setDate(visitedLocation.getDateTimeOfVisitLocationPoint().toLocalDate());
            latestAnalytics.setStatusOfVisit(StatusOfAnimalVisitToArea.ARRIVED);
            analyticsService.save(latestAnalytics);
        }

        return visitedLocationDto;
    }

    private LocationPoint findThePreviousLocationWhereAnimalWasLocated(Animal animal,
                                                                       AnimalVisitedLocation visitedLocation) {

        List<AnimalVisitedLocation> visitedLocations = animal.getVisitedLocations();
        int currentVisitedLocationIndex = visitedLocations.indexOf(visitedLocation);
        if (currentVisitedLocationIndex <= 1) {
            return animal.getChippingLocation();
        } else {
            int previousVisitedLocationIndex = currentVisitedLocationIndex - 1;
            return visitedLocations.get(previousVisitedLocationIndex).getLocationPoint();
        }
    }

}
