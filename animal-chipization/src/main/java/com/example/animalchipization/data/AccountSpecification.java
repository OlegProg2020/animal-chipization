package com.example.animalchipization.data;

import com.example.animalchipization.models.Account;

import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class AccountSpecification implements Specification<Account> {
    private final SearchCriteria<?> criteria;

    public AccountSpecification(SearchCriteria<?> criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Account> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        if (criteria.getValue() == null) {
            return builder.isTrue(builder.literal(true));
        } else {
            return builder.like(builder.lower(root.get(criteria.getKey())),
                    "%" + criteria.getValue().toString().toLowerCase() + "%");
        }
    }
}
