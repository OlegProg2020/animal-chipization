package com.example.animalchipization.data;

import com.example.animalchipization.models.Animal;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class AnimalSpecification implements Specification<Animal> {

    private final SearchCriteria<?> criteria;

    public AnimalSpecification(SearchCriteria<?> criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Animal> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        Predicate result;
        if (criteria.getValue() == null) {
            return builder.isTrue(builder.literal(true));
        } else {
            switch (criteria.getOperation()) {
                case EQUALS -> {
                    result = builder.equal(root.get(criteria.getKey()), criteria.getValue());
                }
                case GREATER_OR_EQUAL -> {
                    result = builder.greaterThanOrEqualTo(root.get(criteria.getKey()), (LocalDateTime) criteria.getValue());
                }
                case LESS_OR_EQUAL -> {
                    result = builder.lessThanOrEqualTo(root.get(criteria.getKey()), (LocalDateTime) criteria.getValue());
                }
                default -> {
                    //predicate always return false if the criteria operation was not found
                    result = builder.isTrue(builder.literal(false));
                }
            }
            return result;
        }
    }
}
