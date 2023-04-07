package com.example.animalchipization.util;

import com.example.animalchipization.entity.Animal;
import com.example.animalchipization.entity.AnimalVisitedLocation;
import com.example.animalchipization.entity.LocationPoint;
import com.example.animalchipization.mapper.Mapper;
import com.example.animalchipization.web.dto.AnimalDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AnimalUtilsImpl implements AnimalUtils {

    private final Mapper<Animal, AnimalDto> animalMapper;

    @Autowired
    public AnimalUtilsImpl(Mapper<Animal, AnimalDto> animalMapper) {
        this.animalMapper = animalMapper;
    }

    @Override
    public boolean checkAnimalAtChippingLocation(AnimalDto animalDto) {
        Animal animal = animalMapper.toEntity(animalDto);
        List<AnimalVisitedLocation> visitedLocations = animal.getVisitedLocations();
        int visitedLocationsSize = visitedLocations.size();
        if (visitedLocationsSize > 0) {
            LocationPoint lastVisitedLocation = visitedLocations.get(visitedLocationsSize - 1).getLocationPoint();
            return lastVisitedLocation.equals(animal.getChippingLocation());
        }
        return true;
    }

}
