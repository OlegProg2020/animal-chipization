package com.example.animalchipization.service.mapper.implementation;

import com.example.animalchipization.dto.AnimalTypeDto;
import com.example.animalchipization.entity.AnimalType;
import com.example.animalchipization.service.mapper.DefaultMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AnimalTypeMapper extends DefaultMapper<AnimalType, AnimalTypeDto> {

    @Autowired
    public AnimalTypeMapper(ModelMapper mapper) {
        super(mapper, AnimalType.class, AnimalTypeDto.class);
    }
}
