package com.example.animalchipization.data.repository.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

@Converter
public class JpaPolygonToJtsPolygonAttributeConverter implements AttributeConverter<Polygon, String> {

    /**
     * When called, returns UnsupportedOperationException, since without
     * using Postgis and Hibernate Spatial there is no data type in JPA
     * corresponding to the SQL type 'polygon'.
     */
    @Override
    public String convertToDatabaseColumn(Polygon attribute) {
        throw new UnsupportedOperationException();
    }

    /**
     * @param dbData the data from the database column to be
     *               converted.
     *               For example: "((5.0,5.0),(-5.0,0.0),(0.0,5.0),(5.0,5.0))".
     * @return the converted value to be stored in the entity
     * attribute.
     */
    @Override
    public Polygon convertToEntityAttribute(String dbData) {
        StringBuffer buffer = new StringBuffer(dbData.
                replaceAll(",", " ").replaceAll("[)] [(]", ", "));
        buffer.insert(0, "POLYGON ");

        GeometryFactory factory = new GeometryFactory();
        WKTReader reader = new WKTReader(factory);
        try {
            Coordinate[] coordinates = reader.read(buffer.toString()).getCoordinates();
            return factory.createPolygon(coordinates);
        } catch (ParseException exception) {
            throw new IllegalArgumentException("Unable to convert JPA String Polygon to JTS Polygon.");
        }
    }

}
