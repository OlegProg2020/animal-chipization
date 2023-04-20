package com.example.animalchipization.data.repository;

import com.example.animalchipization.entity.AreaAnalytics;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface AreaAnalyticsRepository extends CrudRepository<AreaAnalytics, Long>,
        JpaSpecificationExecutor<AreaAnalytics> {

}
