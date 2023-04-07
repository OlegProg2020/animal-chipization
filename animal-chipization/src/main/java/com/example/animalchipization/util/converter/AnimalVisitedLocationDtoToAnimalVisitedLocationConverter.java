package com.example.animalchipization.util.converter;

import com.example.animalchipization.entity.Animal;
import com.example.animalchipization.entity.AnimalVisitedLocation;
import com.example.animalchipization.entity.LocationPoint;
import com.example.animalchipization.mapper.Mapper;
import com.example.animalchipization.service.AnimalService;
import com.example.animalchipization.service.LocationPointService;
import com.example.animalchipization.web.dto.AnimalDto;
import com.example.animalchipization.web.dto.AnimalVisitedLocationDto;
import com.example.animalchipization.web.dto.LocationPointDto;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AnimalVisitedLocationDtoToAnimalVisitedLocationConverter
        implements Converter<AnimalVisitedLocationDto, AnimalVisitedLocation> {

    private final LocationPointService locationPointService;
    private final AnimalService animalService;
    private final Mapper<LocationPoint, LocationPointDto> locationPointMapper;
    private final Mapper<Animal, AnimalDto> animalMapper;

    @Autowired
    public AnimalVisitedLocationDtoToAnimalVisitedLocationConverter(
            LocationPointService locationPointService,
            AnimalService animalService,
            Mapper<LocationPoint, LocationPointDto> locationPointMapper,
            Mapper<Animal, AnimalDto> animalMapper) {

        this.locationPointService = locationPointService;
        this.animalService = animalService;
        this.locationPointMapper = locationPointMapper;
        this.animalMapper = animalMapper;
    }

    @Override
    public AnimalVisitedLocation convert(
            MappingContext<AnimalVisitedLocationDto, AnimalVisitedLocation> mappingContext) {

        AnimalVisitedLocationDto dto = mappingContext.getSource();
        AnimalVisitedLocation entity = new AnimalVisitedLocation();
        entity.setId(dto.getId());
        if (dto.getDateTimeOfVisitLocationPoint() != null) {
            entity.setDateTimeOfVisitLocationPoint(dto.getDateTimeOfVisitLocationPoint());
        }
        entity.setLocationPoint(
                locationPointMapper.toEntity(
                        locationPointService.findLocationPointById(dto.getLocationPointId())
                )
        );
        entity.setAnimal(
                animalMapper.toEntity(
                        animalService.findById(dto.getAnimalId())
                )
        );
        return entity;
    }

}
