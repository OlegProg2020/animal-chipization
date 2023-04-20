package com.example.animalchipization.data.repository;

import com.example.animalchipization.data.projection.IdOnly;
import com.example.animalchipization.entity.AreaAnalytics;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.Collection;

public interface AreaAnalyticsRepository extends CrudRepository<AreaAnalytics, Long>,
        JpaSpecificationExecutor<AreaAnalytics> {

    Collection<IdOnly> findAllByArea_IdAndDateBetween(Long areaId, LocalDate startDate, LocalDate endDate);

    @Query(value = "SELECT DISTINCT ON (animal_id, status_of_visit) * FROM area_analytics WHERE id IN ?",
            nativeQuery = true)
    Collection<IdOnly> findDistinctOnAnimalAndStatusOfVisitAndIdIn(Iterable<Long> ids);

    Collection<AreaAnalytics> findAllByIdIn(Collection<Long> id);

}
