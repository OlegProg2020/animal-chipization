package com.example.animalchipization.util.converter;

import com.example.animalchipization.dto.AreaDto;
import com.example.animalchipization.entity.Area;
import com.example.animalchipization.web.model.Coordinate;
import org.locationtech.jts.geom.Polygon;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AreaToAreaDtoConverter implements org.modelmapper.Converter<Area, AreaDto> {

    private final Converter<Polygon, List<Coordinate>> polygonToCoordinatesConverter;

    @Autowired
    public AreaToAreaDtoConverter(Converter<Polygon, List<Coordinate>> polygonToCoordinatesConverter) {
        this.polygonToCoordinatesConverter = polygonToCoordinatesConverter;
    }

    @Override
    public AreaDto convert(MappingContext<Area, AreaDto> mappingContext) {
        Area entity = mappingContext.getSource();
        AreaDto dto = AreaDto.builder()
                .withId(entity.getId())
                .withName(entity.getName())
                .withAreaPoints(polygonToCoordinatesConverter.convert(entity.getAreaPoints()))
                .build();
        return dto;
    }
}
