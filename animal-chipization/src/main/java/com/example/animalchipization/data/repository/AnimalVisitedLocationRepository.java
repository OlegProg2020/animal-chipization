package com.example.animalchipization.data.repository;

import com.example.animalchipization.model.AnimalVisitedLocation;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalVisitedLocationRepository
        extends CrudRepository<AnimalVisitedLocation, Long>, JpaSpecificationExecutor<AnimalVisitedLocation> {

}
