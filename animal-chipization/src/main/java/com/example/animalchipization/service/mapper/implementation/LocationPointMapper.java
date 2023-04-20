package com.example.animalchipization.service.mapper.implementation;

import com.example.animalchipization.dto.LocationPointDto;
import com.example.animalchipization.entity.LocationPoint;
import com.example.animalchipization.service.mapper.DefaultMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocationPointMapper extends DefaultMapper<LocationPoint, LocationPointDto> {

    @Autowired
    public LocationPointMapper(ModelMapper mapper) {
        super(mapper, LocationPoint.class, LocationPointDto.class);
    }
}
