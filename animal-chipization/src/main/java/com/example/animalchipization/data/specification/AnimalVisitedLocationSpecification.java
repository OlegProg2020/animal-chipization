package com.example.animalchipization.data.specification;

import com.example.animalchipization.entity.AnimalVisitedLocation;
import org.springframework.data.jpa.domain.Specification;

import java.time.ZonedDateTime;

public class AnimalVisitedLocationSpecification {
    public static Specification<AnimalVisitedLocation> hasAnimalId(Long animalId) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("animal").get("id"), animalId);
    }

    public static Specification<AnimalVisitedLocation> hasDateTimeOfVisitLocationPointGreaterThanOrEqualTo(
            ZonedDateTime startDateTime) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (startDateTime != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("dateTimeOfVisitLocationPoint"), startDateTime);
            } else {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
        };
    }

    public static Specification<AnimalVisitedLocation> hasDateTimeOfVisitLocationPointLessThanOrEqualTo(
            ZonedDateTime endDateTime) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (endDateTime != null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("dateTimeOfVisitLocationPoint"), endDateTime);
            } else {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
        };
    }

}
