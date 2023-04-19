package com.example.animalchipization.data.repository.converter;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;
import org.postgresql.geometric.PGpolygon;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class PGpolygonToJtsPolygonConverter implements Converter<PGpolygon, Polygon> {

    /**
     * Convert the PGpolygon to JTS Polygon.
     */
    @Override
    public Polygon convert(PGpolygon source) {
        Coordinate[] coordinates = new Coordinate[0];
        if (source.points != null) {
            coordinates = Arrays.stream(source.points)
                    .map(pgPoint -> new Coordinate(pgPoint.x, pgPoint.y))
                    .toArray(Coordinate[]::new);
        }
        GeometryFactory factory = new GeometryFactory();
        return factory.createPolygon(coordinates);
    }

}
