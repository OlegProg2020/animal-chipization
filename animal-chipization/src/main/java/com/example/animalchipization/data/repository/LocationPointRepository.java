package com.example.animalchipization.data.repository;

import com.example.animalchipization.model.LocationPoint;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationPointRepository extends CrudRepository<LocationPoint, Long> {
    Boolean existsByLatitudeAndLongitudeIs(Double latitude, Double longitude);
}
