package com.example.animalchipization.data.repository;

import com.example.animalchipization.model.AnimalVisitedLocation;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnimalVisitedLocationRepository
        extends CrudRepository<AnimalVisitedLocation, Long>, JpaSpecificationExecutor<AnimalVisitedLocation> {
    Optional<AnimalVisitedLocation> findByAnimal_IdAndLocationPoint_Id(Long animalId, Long locationPointId);
}
