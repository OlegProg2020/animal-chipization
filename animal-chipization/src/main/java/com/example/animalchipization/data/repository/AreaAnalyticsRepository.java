package com.example.animalchipization.data.repository;

import com.example.animalchipization.entity.AreaAnalytics;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface AreaAnalyticsRepository extends CrudRepository<AreaAnalytics, Long>,
        JpaSpecificationExecutor<AreaAnalytics> {

}
