package com.example.animalchipization.data.repository;

import com.example.animalchipization.model.Animal;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalRepository extends CrudRepository<Animal, Long>, JpaSpecificationExecutor<Animal> {

}
