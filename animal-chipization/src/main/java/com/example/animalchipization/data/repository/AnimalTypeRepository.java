package com.example.animalchipization.data.repository;

import com.example.animalchipization.entity.AnimalType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalTypeRepository extends CrudRepository<AnimalType, Long> {

}
