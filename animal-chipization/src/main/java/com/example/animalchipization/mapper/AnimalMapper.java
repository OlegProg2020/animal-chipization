package com.example.animalchipization.mapper;

import com.example.animalchipization.entity.Animal;
import com.example.animalchipization.web.dto.AnimalDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AnimalMapper extends DefaultMapper<Animal, AnimalDto> {

    private final ModelMapper mapper;

    @Autowired
    public AnimalMapper(ModelMapper mapper) {
        super(mapper, Animal.class, AnimalDto.class);
        this.mapper = mapper;
    }



}
