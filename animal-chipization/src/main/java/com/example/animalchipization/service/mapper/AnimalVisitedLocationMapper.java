package com.example.animalchipization.service.mapper;

import com.example.animalchipization.entity.AnimalVisitedLocation;
import com.example.animalchipization.web.dto.AnimalVisitedLocationDto;
import jakarta.annotation.PostConstruct;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AnimalVisitedLocationMapper extends DefaultMapper<AnimalVisitedLocation, AnimalVisitedLocationDto> {

    private final ModelMapper mapper;
    private final Converter<AnimalVisitedLocation, AnimalVisitedLocationDto> entityToDtoConverter;
    private final Converter<AnimalVisitedLocationDto, AnimalVisitedLocation> dtoToEntityConverter;

    @Autowired
    public AnimalVisitedLocationMapper(
            ModelMapper mapper,
            Converter<AnimalVisitedLocation, AnimalVisitedLocationDto> entityToDtoConverter,
            Converter<AnimalVisitedLocationDto, AnimalVisitedLocation> DtoToEntityConverter) {

        super(mapper, AnimalVisitedLocation.class, AnimalVisitedLocationDto.class);
        this.mapper = mapper;
        this.entityToDtoConverter = entityToDtoConverter;
        this.dtoToEntityConverter = DtoToEntityConverter;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(AnimalVisitedLocation.class, AnimalVisitedLocationDto.class)
                .setConverter(entityToDtoConverter);
        mapper.createTypeMap(AnimalVisitedLocationDto.class, AnimalVisitedLocation.class)
                .setConverter(dtoToEntityConverter);
    }
}
