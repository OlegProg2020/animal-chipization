package com.example.animalchipization.data.repository;

import com.example.animalchipization.entity.Area;
import org.locationtech.jts.geom.Polygon;
import org.postgresql.geometric.PGpolygon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.NoSuchElementException;

@Component
public class CustomAreaRepositoryImpl implements CustomAreaRepository {

    private final JdbcTemplate jdbcTemplate;
    private final Converter<Polygon, PGpolygon> jtsPolygonToPGpolygonConverter;

    @Autowired
    public CustomAreaRepositoryImpl(JdbcTemplate jdbcTemplate,
                                    Converter<Polygon, PGpolygon> jtsPolygonToPGpolygonConverter) {
        this.jdbcTemplate = jdbcTemplate;
        this.jtsPolygonToPGpolygonConverter = jtsPolygonToPGpolygonConverter;
    }


    /**
     * Save area and return generated id.
     * If id == null, throws RuntimeException("Failed to retrieve generated id").
     *
     * @param area
     * @return generated id.
     */
    @Transactional
    @Modifying
    public long save(Area area) {
        String sql = "INSERT INTO area (name, area_points) VALUES (?, ?) RETURNING id;";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                    ps.setString(1, area.getName());
                    ps.setObject(2, jtsPolygonToPGpolygonConverter.convert(area.getAreaPoints()));
                    return ps;
                }, keyHolder);
        try {
            return keyHolder.getKey().longValue();
        } catch (NullPointerException exception) {
            throw new RuntimeException("Failed to retrieve generated id");
        }
    }

    @Transactional
    @Modifying
    public void update(Area area) {
        String sql = "UPDATE area SET name = ?, area_points = ? where id = ?;";
        jdbcTemplate.update(sql, area.getName(),
                jtsPolygonToPGpolygonConverter.convert(area.getAreaPoints()),
                area.getId());
    }

    @Override
    public Boolean existsByAreaPoints(Polygon areaPoints) {
        String sql = "SELECT CASE WHEN COUNT(area_points) >= 1 THEN TRUE ELSE FALSE END FROM area WHERE area_points ~= ?";
        return jdbcTemplate.queryForObject(sql, Boolean.class,
                jtsPolygonToPGpolygonConverter.convert(areaPoints));
    }

    @Override
    public Collection<PGpolygon> findAreaOverlapsByAreaPoints(Polygon areaPoints) {
        String sql = "SELECT area_points FROM area WHERE area_points && ?";
        return jdbcTemplate.queryForList(sql, PGpolygon.class,
                jtsPolygonToPGpolygonConverter.convert(areaPoints));
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM area WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        if (rowsAffected == 0) {
            throw new NoSuchElementException();
        }
    }

}
