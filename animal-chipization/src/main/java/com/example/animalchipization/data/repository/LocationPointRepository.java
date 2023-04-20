package com.example.animalchipization.data.repository;

import com.example.animalchipization.entity.LocationPoint;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationPointRepository extends CrudRepository<LocationPoint, Long> {
    Optional<LocationPoint> findByLatitudeAndLongitude(Double latitude, Double longitude);
}
