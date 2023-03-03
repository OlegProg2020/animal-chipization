package com.example.animalchipization.data.specifications;

import com.example.animalchipization.models.AnimalVisitedLocation;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class AnimalVisitedLocationSpecification {
    public static Specification<AnimalVisitedLocation> hasAnimalId(Long animalId) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("animal").get("id"), animalId);
    }

    public static Specification<AnimalVisitedLocation> hasDateTimeOfVisitLocationPointGreaterThanOrEqualTo(
            LocalDateTime startDateTime) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (startDateTime != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("dateTimeOfVisitLocationPoint"), startDateTime);
            } else {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
        };
    }

    public static Specification<AnimalVisitedLocation> hasDateTimeOfVisitLocationPointLessThanOrEqualTo(
            LocalDateTime endDateTime) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (endDateTime != null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("dateTimeOfVisitLocationPoint"), endDateTime);
            } else {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
        };
    }

}
