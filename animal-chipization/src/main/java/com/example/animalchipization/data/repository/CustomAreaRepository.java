package com.example.animalchipization.data.repository;

import com.example.animalchipization.entity.Area;
import com.example.animalchipization.entity.LocationPoint;
import org.locationtech.jts.geom.Polygon;

import java.util.Collection;
import java.util.Optional;

public interface CustomAreaRepository {

    /**
     * @return all Areas that have at least one point
     * in common with the specified one, including
     * inside or on the border.
     */
    Collection<Area> findAreaOverlapsByAreaPoints(Polygon areaPoints);

    /**
     * @return area containing the location point inside or on the border.
     * If not found throws EmptyResultDataAccessException.
     */
    Area findAreaContainingLocationPoint(LocationPoint locationPoint);

    /**
     * Save area and return generated id.
     *
     * @return generated id.
     */
    long save(Area area);

    void update(Area area);

    /**
     * Delete Area by id. If the area is not found
     * throws NoSuchElementException.
     */
    void deleteById(Long id);

}
