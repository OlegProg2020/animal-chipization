package com.example.animalchipization.data.repository;

import com.example.animalchipization.entity.Area;
import org.springframework.data.repository.Repository;

import java.util.Optional;

@org.springframework.stereotype.Repository
public interface AreaRepository extends Repository<Area, Long>, CustomAreaRepository {

    Optional<Area> findById(Long id);

    boolean existsById(Long id);
}
