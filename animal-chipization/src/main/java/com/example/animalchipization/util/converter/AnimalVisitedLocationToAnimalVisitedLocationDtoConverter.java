package com.example.animalchipization.util.converter;

import com.example.animalchipization.entity.AnimalVisitedLocation;
import com.example.animalchipization.web.dto.AnimalVisitedLocationDto;
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
                .withAnimalId(entity.getAnimal().getId())
                .withLocationPointId(entity.getLocationPoint().getId())
                .build();
        dto.setId(entity.getId());
        dto.setDateTimeOfVisitLocationPoint(entity.getDateTimeOfVisitLocationPoint());
        return dto;
    }

}
