package com.example.animalchipization.util.converter.animal;

import com.example.animalchipization.entity.*;
import com.example.animalchipization.mapper.Mapper;
import com.example.animalchipization.service.AccountService;
import com.example.animalchipization.service.AnimalTypeService;
import com.example.animalchipization.service.AnimalVisitedLocationService;
import com.example.animalchipization.service.LocationPointService;
import com.example.animalchipization.web.dto.*;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
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
                .map(id -> animalTypeMapper.toEntity(animalTypeService.findAnimalTypeById(id)))
                .collect(Collectors.toSet()));
        entity.setWeight(dto.getWeight());
        entity.setLength(dto.getLength());
        entity.setHeight(dto.getHeight());
        entity.setGender(dto.getGender());
        entity.setLifeStatus(dto.getLifeStatus());
        entity.setChippingDateTime(dto.getChippingDateTime());
        entity.setChipper(accountMapper.toEntity(accountService.findAccountById(dto.getChipperId())));
        entity.setChippingLocation(locationPointMapper.toEntity(locationPointService.findLocationPointById(dto.getChippingLocationId())));
        entity.setVisitedLocations(dto.getVisitedLocations().stream()
                .map(id -> animalVisitedLocationMapper.toEntity(animalVisitedLocationService.findById(id)))
                .collect(Collectors.toCollection(LinkedList::new)));
        entity.setDeathDateTime(dto.getDeathDateTime());
        return entity;
    }

}
