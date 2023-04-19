package com.example.animalchipization.data.repository;

import com.example.animalchipization.entity.Area;
import org.locationtech.jts.geom.Polygon;

import java.util.Collection;

public interface CustomAreaRepository {

    /**
     * Save area and return generated id.
     *
     * @return generated id.
     */
    long save(Area area);

    void update(Area area);

    /**
     * @return all Areas that have at least one point
     * in common with the specified one, including
     * inside or on the border.
     */
    Collection<Area> findAreaOverlapsByAreaPoints(Polygon areaPoints);

    void deleteById(Long id);

}
