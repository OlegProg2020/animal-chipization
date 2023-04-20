package com.example.animalchipization.service.mapper.converter;

import com.example.animalchipization.dto.AnimalVisitedLocationDto;
import com.example.animalchipization.entity.AnimalVisitedLocation;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

@Component
public class AnimalVisitedLocationToAnimalVisitedLocationDtoConverter
        implements Converter<AnimalVisitedLocation, AnimalVisitedLocationDto> {

    @Override
    public AnimalVisitedLocationDto convert(MappingContext<AnimalVisitedLocation,
            AnimalVisitedLocationDto> mappingContext) {

        AnimalVisitedLocation entity = mappingContext.getSource();
        AnimalVisitedLocationDto dto = AnimalVisitedLocationDto.builder()
                .withId(entity.getId())
                .withDateTimeOfVisitLocationPoint(entity.getDateTimeOfVisitLocationPoint())
                .withAnimalId(entity.getAnimal().getId())
                .withLocationPointId(entity.getLocationPoint().getId())
                .build();
        return dto;
    }

}
