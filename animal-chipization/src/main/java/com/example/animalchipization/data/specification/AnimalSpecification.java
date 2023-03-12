package com.example.animalchipization.data.specification;

import com.example.animalchipization.model.enums.LifeStatus;
import com.example.animalchipization.model.enums.Gender;
import com.example.animalchipization.model.Animal;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class AnimalSpecification {

    public static Specification<Animal> hasChippingDateTimeGreaterThanOrEqualTo(LocalDateTime startDateTime) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (startDateTime != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("chippingDateTime"), startDateTime);
            } else {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
        };
    }

    public static Specification<Animal> hasChippingDateTimeLessThanOrEqualTo(LocalDateTime endDateTime) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (endDateTime != null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("chippingDateTime"), endDateTime);
            } else {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
        };
    }

    public static Specification<Animal> hasChipperId(Long chipperId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (chipperId != null) {
                return criteriaBuilder.equal(root.get("chipper").get("id"), chipperId);
            } else {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
        };
    }

    public static Specification<Animal> hasChippingLocationId(Long chippingLocationId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (chippingLocationId != null) {
                return criteriaBuilder.equal(root.get("chippingLocation").get("id"), chippingLocationId);
            } else {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
        };
    }

    public static Specification<Animal> hasLifeStatus(LifeStatus lifeStatus) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (lifeStatus != null) {
                return criteriaBuilder.equal(root.get("lifeStatus"), lifeStatus);
            } else {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
        };
    }

    public static Specification<Animal> hasGender(Gender gender) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (gender != null) {
                return criteriaBuilder.equal(root.get("gender"), gender);
            } else {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
        };
    }

}
