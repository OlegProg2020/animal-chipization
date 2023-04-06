package com.example.animalchipization.mapper;

import com.example.animalchipization.entity.AnimalType;
import com.example.animalchipization.web.dto.AnimalTypeDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AnimalTypeMapper implements Mapper<AnimalType, AnimalTypeDto> {

    private final ModelMapper mapper;

    @Autowired
    public AnimalTypeMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public AnimalType toEntity(AnimalTypeDto dto) {
        return Objects.isNull(dto) ? null : mapper.map(dto, AnimalType.class);
    }

    @Override
    public AnimalTypeDto toDto(AnimalType entity) {
        return Objects.isNull(entity) ? null : mapper.map(entity, AnimalTypeDto.class);
    }
}
