package com.example.animalchipization.data.repository.converter;

import org.locationtech.jts.geom.Polygon;
import org.postgresql.geometric.PGpoint;
import org.postgresql.geometric.PGpolygon;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class JtsPolygonToPGpolygonConverter implements Converter<Polygon, PGpolygon> {

    /**
     * Convert the JTS Polygon to PGpolygon that can be
     * saved in PostgreSQL database using JDBC.
     */
    @Override
    public PGpolygon convert(Polygon source) {
        PGpoint[] pgPoints = Arrays.stream(source.getCoordinates())
                .map(coordinate -> new PGpoint(coordinate.getX(), coordinate.getY()))
                .toArray(PGpoint[]::new);
        return new PGpolygon(pgPoints);
    }
}
