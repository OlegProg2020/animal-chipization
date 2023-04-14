package com.example.animalchipization.data.repository;

import com.example.animalchipization.entity.Area;
import org.locationtech.jts.geom.Polygon;
import org.postgresql.geometric.PGpolygon;

import java.util.Collection;

public interface CustomAreaRepository {

    void save(Area area);

    void update(Area area);

    Boolean existsByAreaPoints(Polygon areaPoints);

    Collection<PGpolygon> findAreaOverlapsByAreaPoints(Polygon areaPoints);
}
