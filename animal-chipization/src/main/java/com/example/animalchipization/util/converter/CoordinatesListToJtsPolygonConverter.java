package com.example.animalchipization.util.converter;

import com.example.animalchipization.exception.PointsOfLinearRingDoNotFormClosedLinestring;
import com.example.animalchipization.web.model.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CoordinatesListToJtsPolygonConverter implements Converter<List<Coordinate>, Polygon> {

    @Override
    public Polygon convert(List<Coordinate> source) {
        org.locationtech.jts.geom.Coordinate[] jstCoordinates = source.stream()
                .map(coordinate -> new org.locationtech.jts.geom.Coordinate(
                        coordinate.getLongitude(), coordinate.getLatitude()))
                .toArray(org.locationtech.jts.geom.Coordinate[]::new);
        GeometryFactory factory = new GeometryFactory();
        try {
            return factory.createPolygon(jstCoordinates);
        } catch (IllegalArgumentException exception) {
            throw new PointsOfLinearRingDoNotFormClosedLinestring();
        }
    }
}
