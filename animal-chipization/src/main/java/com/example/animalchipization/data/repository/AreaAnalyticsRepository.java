package com.example.animalchipization.data.repository;

import com.example.animalchipization.data.projection.IdOnly;
import com.example.animalchipization.entity.AreaAnalytics;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface AreaAnalyticsRepository extends CrudRepository<AreaAnalytics, Long>,
        JpaSpecificationExecutor<AreaAnalytics> {

    Collection<IdOnly> findAllIds(Specification<AreaAnalytics> specification);

    @Query(value = "SELECT DISTINCT ON (animal_id, status_of_visit) * FROM area_analytics WHERE id IN ?",
            nativeQuery = true)
    Collection<AreaAnalytics> findDistinctOnAnimalAndStatusOfVisitAndIdIn(Iterable<Long> ids);

}
