package com.example.animalchipization.util.converter;

import com.example.animalchipization.dto.AnimalDto;
import com.example.animalchipization.dto.AnimalVisitedLocationDto;
import com.example.animalchipization.dto.LocationPointDto;
import com.example.animalchipization.entity.Animal;
import com.example.animalchipization.entity.AnimalVisitedLocation;
import com.example.animalchipization.entity.LocationPoint;
import com.example.animalchipization.service.AnimalService;
import com.example.animalchipization.service.LocationPointService;
import com.example.animalchipization.service.mapper.Mapper;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
            @Lazy LocationPointService locationPointService,
            @Lazy AnimalService animalService,
            Mapper<LocationPoint, LocationPointDto> locationPointMapper,
            @Lazy Mapper<Animal, AnimalDto> animalMapper) {

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
                        locationPointService.findById(dto.getLocationPointId())
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
