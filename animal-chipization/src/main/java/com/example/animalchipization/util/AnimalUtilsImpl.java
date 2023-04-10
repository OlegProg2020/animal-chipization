package com.example.animalchipization.util;

import com.example.animalchipization.entity.Animal;
import com.example.animalchipization.mapper.Mapper;
import com.example.animalchipization.web.dto.AnimalDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AnimalUtilsImpl implements AnimalUtils {

    private final Mapper<Animal, AnimalDto> animalMapper;

    @Autowired
    public AnimalUtilsImpl(Mapper<Animal, AnimalDto> animalMapper) {
        this.animalMapper = animalMapper;
    }


}
