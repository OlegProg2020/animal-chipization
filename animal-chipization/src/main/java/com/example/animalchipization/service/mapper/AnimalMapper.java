package com.example.animalchipization.service.mapper;

import com.example.animalchipization.dto.AnimalDto;
import com.example.animalchipization.entity.Animal;
import jakarta.annotation.PostConstruct;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AnimalMapper extends DefaultMapper<Animal, AnimalDto> {

    private final ModelMapper mapper;
    private final Converter<Animal, AnimalDto> entityToDtoConverter;
    private final Converter<AnimalDto, Animal> dtoToEntityConverter;

    @Autowired
    public AnimalMapper(ModelMapper mapper,
                        Converter<Animal, AnimalDto> entityToDtoConverter,
                        Converter<AnimalDto, Animal> dtoToEntityConverter) {

        super(mapper, Animal.class, AnimalDto.class);
        this.mapper = mapper;
        this.entityToDtoConverter = entityToDtoConverter;
        this.dtoToEntityConverter = dtoToEntityConverter;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Animal.class, AnimalDto.class)
                .setConverter(entityToDtoConverter);
        mapper.createTypeMap(AnimalDto.class, Animal.class)
                .setConverter(dtoToEntityConverter);
    }

}
