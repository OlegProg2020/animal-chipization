package com.example.animalchipization.data.repository;

import com.example.animalchipization.model.AnimalType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalTypeRepository extends CrudRepository<AnimalType, Long> {

    Boolean existsByType(String type);
}
