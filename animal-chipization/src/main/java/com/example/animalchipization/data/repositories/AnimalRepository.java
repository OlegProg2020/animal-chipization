package com.example.animalchipization.data.repositories;

import com.example.animalchipization.models.Animal;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalRepository extends CrudRepository<Animal, Long>, JpaSpecificationExecutor<Animal> {

}
