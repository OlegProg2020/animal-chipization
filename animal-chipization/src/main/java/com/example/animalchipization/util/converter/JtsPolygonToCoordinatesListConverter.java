package com.example.animalchipization.util.converter;

import com.example.animalchipization.web.model.Coordinate;
import org.locationtech.jts.geom.Polygon;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JtsPolygonToCoordinatesListConverter implements Converter<Polygon, List<Coordinate>> {

    @Override
    public List<Coordinate> convert(MappingContext<Polygon, List<Coordinate>> mappingContext) {
        Polygon polygon = mappingContext.getSource();
        return Arrays.stream(polygon.getCoordinates())
                .map(coordinate -> new Coordinate(coordinate.getX(), coordinate.getY()))
                .collect(Collectors.toList());
    }
}
