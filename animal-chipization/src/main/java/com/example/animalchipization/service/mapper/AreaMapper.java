package com.example.animalchipization.service.mapper;

import com.example.animalchipization.dto.AreaDto;
import com.example.animalchipization.entity.Area;
import jakarta.annotation.PostConstruct;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AreaMapper extends DefaultMapper<Area, AreaDto> {

    private final ModelMapper mapper;
    private final Converter<Area, AreaDto> entityToDtoConverter;
    private final Converter<AreaDto, Area> dtoToEntityConverter;

    @Autowired
    public AreaMapper(ModelMapper mapper,
                      Converter<Area, AreaDto> entityToDtoConverter,
                      Converter<AreaDto, Area> dtoToEntityConverter) {

        super(mapper, Area.class, AreaDto.class);
        this.mapper = mapper;
        this.entityToDtoConverter = entityToDtoConverter;
        this.dtoToEntityConverter = dtoToEntityConverter;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Area.class, AreaDto.class)
                .setConverter(entityToDtoConverter);
        mapper.createTypeMap(AreaDto.class, Area.class)
                .setConverter(dtoToEntityConverter);
    }

}
