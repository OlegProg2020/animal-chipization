package com.example.animalchipization.data;

import com.example.animalchipization.models.AnimalType;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalTypeRepository extends CrudRepository<AnimalType, Long> {

}
