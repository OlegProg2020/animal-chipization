package com.example.animalchipization.data.repository;

import com.example.animalchipization.entity.AnimalType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface AnimalTypeRepository extends CrudRepository<AnimalType, Long> {
    Collection<AnimalType> findAllByIdIn(Collection<Long> ids);
}
