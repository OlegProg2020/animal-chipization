package com.example.animalchipization.mapper;

import com.example.animalchipization.entity.AnimalType;
import com.example.animalchipization.entity.AnimalVisitedLocation;
import com.example.animalchipization.web.dto.AnimalTypeDto;
import com.example.animalchipization.web.dto.AnimalVisitedLocationDto;
import jakarta.annotation.PostConstruct;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AnimalVisitedLocationMapper implements Mapper<AnimalVisitedLocation, AnimalVisitedLocationDto> {

    private final ModelMapper mapper;
    private final Converter<AnimalVisitedLocation, AnimalVisitedLocationDto> entityToDtoConverter;
    private final Converter<AnimalVisitedLocationDto, AnimalVisitedLocation> DtoToEntityConverter;

    @Autowired
    public AnimalVisitedLocationMapper(ModelMapper mapper,
                                       Converter<AnimalVisitedLocation, AnimalVisitedLocationDto> entityToDtoConverter,
                                       Converter<AnimalVisitedLocationDto, AnimalVisitedLocation> DtoToEntityConverter) {
        this.mapper = mapper;
        this.entityToDtoConverter = entityToDtoConverter;
        this.DtoToEntityConverter = DtoToEntityConverter;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(AnimalVisitedLocation.class, AnimalVisitedLocationDto.class)
                .setConverter(entityToDtoConverter);
        mapper.createTypeMap(AnimalVisitedLocationDto.class, AnimalVisitedLocation.class)
                .setConverter(DtoToEntityConverter);
    }

    @Override
    public AnimalVisitedLocation toEntity(AnimalVisitedLocationDto dto) {
        return Objects.isNull(dto) ? null : mapper.map(dto, AnimalVisitedLocation.class);
    }

    @Override
    public AnimalVisitedLocationDto toDto(AnimalVisitedLocation entity) {
        return Objects.isNull(entity) ? null : mapper.map(entity, AnimalVisitedLocationDto.class);
    }

}
