package com.example.animalchipization.mapper;

import com.example.animalchipization.entity.LocationPoint;
import com.example.animalchipization.web.dto.LocationPointDto;
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
