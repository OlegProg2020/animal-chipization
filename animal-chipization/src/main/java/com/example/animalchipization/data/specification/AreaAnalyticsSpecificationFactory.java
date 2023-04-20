package com.example.animalchipization.data.specification;

import com.example.animalchipization.entity.AreaAnalytics;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.ZonedDateTime;

public class AreaAnalyticsSpecificationFactory {

    public static Specification<AreaAnalytics> hasAreaId(Long areaId) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("area").get("id"), areaId);
    }

    public static Specification<AreaAnalytics> hasDateGreaterThanOrEqualTo(LocalDate startDate) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("date"), startDate);
    }

    public static Specification<AreaAnalytics> hasDateLessThanOrEqualTo(LocalDate endDate) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("date"), endDate);
    }

}
