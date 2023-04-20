package com.example.animalchipization.data.repository.converter;

import com.example.animalchipization.entity.Area;
import org.locationtech.jts.geom.Polygon;
import org.postgresql.geometric.PGpolygon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AreaRowMapper implements RowMapper<Area> {

    private final Converter<PGpolygon, Polygon> pgPolygonToJtsPolygonConverter;

    @Autowired
    public AreaRowMapper(Converter<PGpolygon, Polygon> pgPolygonToJtsPolygonConverter) {
        this.pgPolygonToJtsPolygonConverter = pgPolygonToJtsPolygonConverter;
    }

    @Override
    public Area mapRow(ResultSet rs, int rowNum) throws SQLException {
        Area area = new Area();
        area.setId(rs.getLong("id"));
        area.setName(rs.getString("name"));
        area.setAreaPoints(pgPolygonToJtsPolygonConverter.convert(
                rs.getObject("area_points", PGpolygon.class)));
        return area;
    }
}
