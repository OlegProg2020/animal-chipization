package com.example.animalchipization.service.mapper.converter;

import com.example.animalchipization.data.repository.AccountRepository;
import com.example.animalchipization.data.repository.AnimalTypeRepository;
import com.example.animalchipization.data.repository.AnimalVisitedLocationRepository;
import com.example.animalchipization.data.repository.LocationPointRepository;
import com.example.animalchipization.dto.AnimalDto;
import com.example.animalchipization.entity.Animal;
import com.example.animalchipization.entity.AnimalVisitedLocation;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;

@Component
public class AnimalDtoToAnimalConverter implements Converter<AnimalDto, Animal> {

    private final AnimalTypeRepository animalTypeRepository;
    private final AccountRepository accountRepository;
    private final LocationPointRepository locationPointRepository;
    private final AnimalVisitedLocationRepository animalVisitedLocationRepository;

    @Autowired
    public AnimalDtoToAnimalConverter(
            AnimalTypeRepository animalTypeRepository,
            AccountRepository accountRepository,
            LocationPointRepository locationPointRepository,
            AnimalVisitedLocationRepository animalVisitedLocationRepository) {

        this.animalTypeRepository = animalTypeRepository;
        this.accountRepository = accountRepository;
        this.locationPointRepository = locationPointRepository;
        this.animalVisitedLocationRepository = animalVisitedLocationRepository;
    }

    @Override
    public Animal convert(MappingContext<AnimalDto, Animal> mappingContext) {
        AnimalDto dto = mappingContext.getSource();
        Animal entity = new Animal();
        entity.setId(dto.getId());
        entity.setAnimalTypes(new HashSet<>(animalTypeRepository.findAllByIdIn(dto.getAnimalTypes())));
        entity.setWeight(dto.getWeight());
        entity.setLength(dto.getLength());
        entity.setHeight(dto.getHeight());
        entity.setGender(dto.getGender());
        entity.setLifeStatus(dto.getLifeStatus());
        entity.setChippingDateTime(dto.getChippingDateTime());
        entity.setChipper(accountRepository.findById(dto.getChipperId())
                .orElseThrow(NoSuchElementException::new));
        entity.setChippingLocation(locationPointRepository.findById(dto.getChippingLocationId())
                .orElseThrow(NoSuchElementException::new));
        List<AnimalVisitedLocation> visitedLocations = animalVisitedLocationRepository
                .findAllByIdIn(dto.getVisitedLocations()).stream().toList();
        entity.setVisitedLocations(visitedLocations);
        entity.setDeathDateTime(dto.getDeathDateTime());
        return entity;
    }

}
