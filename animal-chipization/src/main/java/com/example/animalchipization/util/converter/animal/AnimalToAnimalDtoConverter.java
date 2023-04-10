package com.example.animalchipization.util.converter.animal;

import com.example.animalchipization.entity.Animal;
import com.example.animalchipization.entity.AnimalType;
import com.example.animalchipization.entity.AnimalVisitedLocation;
import com.example.animalchipization.web.dto.AnimalDto;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.stream.Collectors;

@Component
public class AnimalToAnimalDtoConverter implements Converter<Animal, AnimalDto> {

    @Override
    public AnimalDto convert(MappingContext<Animal, AnimalDto> mappingContext) {
        Animal entity = mappingContext.getSource();
        AnimalDto dto = AnimalDto.builder()
                .withId(entity.getId())
                .withAnimalTypes(entity.getAnimalTypes().stream().map(AnimalType::getId).collect(Collectors.toSet()))
                .withWeight(entity.getWeight())
                .withLength(entity.getLength())
                .withHeight(entity.getHeight())
                .withGender(entity.getGender())
                .withLifeStatus(entity.getLifeStatus())
                .withChippingDateTime(entity.getChippingDateTime())
                .withChipperId(entity.getChipper().getId())
                .withChippingLocationId(entity.getChippingLocation().getId())
                .withVisitedLocations(entity.getVisitedLocations().stream()
                        .map(AnimalVisitedLocation::getId)
                        .collect(Collectors.toCollection(LinkedList::new)))
                .withDeathDateTime(entity.getDeathDateTime())
                .build();
        return dto;
    }

}
