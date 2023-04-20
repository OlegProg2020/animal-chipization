package com.example.animalchipization.service.mapper.converter;

import com.example.animalchipization.data.repository.AnimalRepository;
import com.example.animalchipization.data.repository.LocationPointRepository;
import com.example.animalchipization.dto.AnimalVisitedLocationDto;
import com.example.animalchipization.entity.AnimalVisitedLocation;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class AnimalVisitedLocationDtoToAnimalVisitedLocationConverter
        implements Converter<AnimalVisitedLocationDto, AnimalVisitedLocation> {

    private final LocationPointRepository locationPointRepository;
    private final AnimalRepository animalRepository;

    @Autowired
    public AnimalVisitedLocationDtoToAnimalVisitedLocationConverter(
            LocationPointRepository locationPointRepository,
            AnimalRepository animalRepository) {

        this.locationPointRepository = locationPointRepository;
        this.animalRepository = animalRepository;
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
        entity.setLocationPoint(locationPointRepository.findById(dto.getLocationPointId())
                .orElseThrow(NoSuchElementException::new));
        entity.setAnimal(animalRepository.findById(dto.getAnimalId())
                .orElseThrow(NoSuchElementException::new)
        );

        return entity;
    }

}
