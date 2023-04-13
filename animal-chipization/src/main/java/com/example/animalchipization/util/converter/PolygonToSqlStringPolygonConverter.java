package com.example.animalchipization.util.converter;

import org.springframework.core.convert.converter.Converter;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Polygon;
import org.springframework.stereotype.Component;

@Component
public class PolygonToSqlStringPolygonConverter implements Converter<Polygon, String> {

    @Override
    public String convert(Polygon source) {
        StringBuffer builder = new StringBuffer();
        Coordinate[] coordinates = source.getCoordinates();
        for(Coordinate coordinate : coordinates) {
            builder.append("(").append(coordinate.getX()).append(",").append(coordinate.getY()).append("),");
        }
        // removing the extra comma "," in the end if necessary
        return builder.substring(0, Math.max(builder.length() - 1, 0));
    }
}
