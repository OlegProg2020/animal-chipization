package com.example.animalchipization.util.converter;

import com.example.animalchipization.dto.*;
import com.example.animalchipization.entity.*;
import com.example.animalchipization.service.AccountService;
import com.example.animalchipization.service.AnimalTypeService;
import com.example.animalchipization.service.AnimalVisitedLocationService;
import com.example.animalchipization.service.LocationPointService;
import com.example.animalchipization.service.mapper.Mapper;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AnimalDtoToAnimalConverter implements Converter<AnimalDto, Animal> {

    private final AnimalTypeService animalTypeService;
    private final AccountService accountService;
    private final LocationPointService locationPointService;
    private final AnimalVisitedLocationService animalVisitedLocationService;
    private final Mapper<AnimalType, AnimalTypeDto> animalTypeMapper;
    private final Mapper<Account, AccountDto> accountMapper;
    private final Mapper<LocationPoint, LocationPointDto> locationPointMapper;
    private final Mapper<AnimalVisitedLocation, AnimalVisitedLocationDto> animalVisitedLocationMapper;

    @Autowired
    public AnimalDtoToAnimalConverter(
            AnimalTypeService animalTypeService,
            AccountService accountService,
            LocationPointService locationPointService,
            AnimalVisitedLocationService animalVisitedLocationService,
            Mapper<AnimalType, AnimalTypeDto> animalTypeMapper,
            Mapper<Account, AccountDto> accountMapper,
            Mapper<LocationPoint, LocationPointDto> locationPointMapper,
            Mapper<AnimalVisitedLocation, AnimalVisitedLocationDto> animalVisitedLocationMapper) {

        this.animalTypeService = animalTypeService;
        this.accountService = accountService;
        this.locationPointService = locationPointService;
        this.animalVisitedLocationService = animalVisitedLocationService;
        this.animalTypeMapper = animalTypeMapper;
        this.accountMapper = accountMapper;
        this.locationPointMapper = locationPointMapper;
        this.animalVisitedLocationMapper = animalVisitedLocationMapper;
    }

    @Override
    public Animal convert(MappingContext<AnimalDto, Animal> mappingContext) {
        AnimalDto dto = mappingContext.getSource();
        Animal entity = new Animal();
        entity.setId(dto.getId());
        entity.setAnimalTypes(dto.getAnimalTypes().stream()
                .map(id -> animalTypeMapper.toEntity(animalTypeService.findById(id)))
                .collect(Collectors.toSet()));
        entity.setWeight(dto.getWeight());
        entity.setLength(dto.getLength());
        entity.setHeight(dto.getHeight());
        entity.setGender(dto.getGender());
        entity.setLifeStatus(dto.getLifeStatus());
        entity.setChippingDateTime(dto.getChippingDateTime());
        entity.setChipper(accountMapper.toEntity(accountService.findById(dto.getChipperId())));
        entity.setChippingLocation(locationPointMapper.toEntity(locationPointService.findById(dto.getChippingLocationId())));
        List<AnimalVisitedLocation> visitedLocations = animalVisitedLocationService
                .findAllById(dto.getVisitedLocations()).stream()
                .map(animalVisitedLocationMapper::toEntity)
                .collect(Collectors.toCollection(LinkedList::new));
        entity.setVisitedLocations(visitedLocations);
        entity.setDeathDateTime(dto.getDeathDateTime());
        return entity;
    }

}
