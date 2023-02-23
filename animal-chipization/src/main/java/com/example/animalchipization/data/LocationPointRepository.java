package com.example.animalchipization.data;

import com.example.animalchipization.models.LocationPoint;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationPointRepository extends CrudRepository<LocationPoint, Long> {

}
