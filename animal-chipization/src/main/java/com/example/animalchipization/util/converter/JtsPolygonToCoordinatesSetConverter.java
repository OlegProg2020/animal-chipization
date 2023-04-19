package com.example.animalchipization.util.converter;

import com.example.animalchipization.web.model.Coordinate;
import org.locationtech.jts.geom.Polygon;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

@Component
public class JtsPolygonToCoordinatesSetConverter implements Converter<Polygon, LinkedHashSet<Coordinate>> {

    /**
     * Converts jts Polygon to Coordinates Set, while
     * removing the last coordinate that corresponds
     * to the first, if necessary.
     * Note: the x coordinate corresponds to longitude, and y to latitude.
     */
    @Override
    public LinkedHashSet<Coordinate> convert(Polygon source) {
        return Arrays.stream(source.getCoordinates())
                .map(coordinate -> new Coordinate(coordinate.getY(), coordinate.getX()))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
