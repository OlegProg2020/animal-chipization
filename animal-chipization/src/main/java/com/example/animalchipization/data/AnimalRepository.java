package com.example.animalchipization.data;

import com.example.animalchipization.models.Animal;

import org.springframework.data.repository.CrudRepository;

public interface AnimalRepository extends CrudRepository<Animal, Long> {

}
