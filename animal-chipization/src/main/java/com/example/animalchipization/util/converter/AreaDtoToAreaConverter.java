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
public class AreaDtoToAreaConverter implements org.modelmapper.Converter<AreaDto, Area> {

    private final Converter<List<Coordinate>, Polygon> coordinatesToPolygonConverter;

    @Autowired
    public AreaDtoToAreaConverter(Converter<List<Coordinate>, Polygon> coordinatesToPolygonConverter) {
        this.coordinatesToPolygonConverter = coordinatesToPolygonConverter;
    }

    @Override
    public Area convert(MappingContext<AreaDto, Area> mappingContext) {
        AreaDto dto = mappingContext.getSource();
        Area entity = new Area();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setAreaPoints(coordinatesToPolygonConverter.convert(dto.getAreaPoints()));
        return entity;
    }
}
