package com.example.animalchipization.util.converter;

import com.example.animalchipization.web.model.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CoordinatesListToJtsPolygonConverter implements Converter<List<Coordinate>, Polygon> {

    @Override
    public Polygon convert(MappingContext<List<Coordinate>, Polygon> mappingContext) {
        List<Coordinate> coordinates = mappingContext.getSource();
        org.locationtech.jts.geom.Coordinate[] jstCoordinates = coordinates.stream()
                .map(coordinate -> new org.locationtech.jts.geom.Coordinate(
                        coordinate.getLatitude(), coordinate.getLongitude()))
                .toArray(org.locationtech.jts.geom.Coordinate[]::new);
        GeometryFactory factory = new GeometryFactory();
        return factory.createPolygon(jstCoordinates);
    }
}
