package com.example.animalchipization.util.converter;

import com.example.animalchipization.exception.PointsOfLinearRingDoNotFormClosedLinestring;
import com.example.animalchipization.web.model.Coordinate;
import org.locationtech.jts.geom.CoordinateList;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class CoordinatesSetToJtsPolygonConverter implements Converter<Set<Coordinate>, Polygon> {

    @Override
    public Polygon convert(Set<Coordinate> source) {
        CoordinateList jtsCoordinates = new CoordinateList();
        for (Coordinate coordinate : source) {
            jtsCoordinates.add(new org.locationtech.jts.geom.Coordinate(
                    coordinate.getLongitude(), coordinate.getLatitude()
            ));
        }
        jtsCoordinates.closeRing();

        GeometryFactory factory = new GeometryFactory();
        try {
            return factory.createPolygon(jtsCoordinates.toCoordinateArray());
        } catch (IllegalArgumentException exception) {
            throw new PointsOfLinearRingDoNotFormClosedLinestring();
        }
    }

}
