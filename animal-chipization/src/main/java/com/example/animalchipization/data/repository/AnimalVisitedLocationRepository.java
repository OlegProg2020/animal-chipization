package com.example.animalchipization.data.repository;

import com.example.animalchipization.entity.AnimalVisitedLocation;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface AnimalVisitedLocationRepository
        extends CrudRepository<AnimalVisitedLocation, Long>, JpaSpecificationExecutor<AnimalVisitedLocation> {

    Collection<AnimalVisitedLocation> findAllByIdIn(Collection<Long> ids);

}
