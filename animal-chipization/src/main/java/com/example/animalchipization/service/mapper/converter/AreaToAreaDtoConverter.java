package com.example.animalchipization.service.mapper.converter;

import com.example.animalchipization.dto.AreaDto;
import com.example.animalchipization.entity.Area;
import com.example.animalchipization.web.model.Coordinate;
import org.locationtech.jts.geom.Polygon;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;

@Component
public class AreaToAreaDtoConverter implements org.modelmapper.Converter<Area, AreaDto> {

    private final Converter<Polygon, LinkedHashSet<Coordinate>> polygonToCoordinatesConverter;

    @Autowired
    public AreaToAreaDtoConverter(Converter<Polygon,
            LinkedHashSet<Coordinate>> polygonToCoordinatesConverter) {
        this.polygonToCoordinatesConverter = polygonToCoordinatesConverter;
    }

    @Override
    public AreaDto convert(MappingContext<Area, AreaDto> mappingContext) {
        Area entity = mappingContext.getSource();
        return AreaDto.builder()
                .withId(entity.getId())
                .withName(entity.getName())
                .withAreaPoints(polygonToCoordinatesConverter.convert(entity.getAreaPoints()))
                .build();
    }
}
