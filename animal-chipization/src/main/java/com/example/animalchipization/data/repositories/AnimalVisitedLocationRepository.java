package com.example.animalchipization.data.repositories;

import com.example.animalchipization.models.AnimalVisitedLocation;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalVisitedLocationRepository
        extends CrudRepository<AnimalVisitedLocation, Long>, JpaSpecificationExecutor<AnimalVisitedLocation> {

}
