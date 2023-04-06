package com.example.animalchipization.mapper;

import com.example.animalchipization.entity.LocationPoint;
import com.example.animalchipization.web.dto.LocationPointDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class LocationPointMapper implements Mapper<LocationPoint, LocationPointDto> {

    private final ModelMapper mapper;

    @Autowired
    public LocationPointMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public LocationPoint toEntity(LocationPointDto dto) {
        return Objects.isNull(dto) ? null : mapper.map(dto, LocationPoint.class);
    }

    @Override
    public LocationPointDto toDto(LocationPoint entity) {
        return Objects.isNull(entity) ? null : mapper.map(entity, LocationPointDto.class);
    }
}
