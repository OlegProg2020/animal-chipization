package com.example.animalchipization.data.repository;

import com.example.animalchipization.entity.Area;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AreaRepository extends Repository<Area, Long> {

    /*@Query(value = "SELECT CASE WHEN COUNT(area_points) >= 1 THEN TRUE ELSE FALSE FROM area " +
            "WHERE area_points && :polygon", nativeQuery = true)
    boolean hasAreaPointsOverlap(@Param("polygon") String polygon);

    @Query(value = "SELECT CASE WHEN COUNT(area_points) >= 1 THEN TRUE ELSE FALSE FROM area " +
            "WHERE area_points ~= :polygon", nativeQuery = true)
    boolean existsAreaByAreaPoints(@Param("polygon") String polygon);*/

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO area (id, name, area_points) VALUES (10, 'name10', '(5,5),(-5,0),(0,5),(5,5)');",
            nativeQuery = true)
    void saveArea();

    Optional<Area> findById(Long id);

}
