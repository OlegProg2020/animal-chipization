package com.example.animalchipization.data.specifications;

import com.example.animalchipization.models.Account;

import org.springframework.data.jpa.domain.Specification;

public class AccountSpecification {

    public static Specification<Account> hasFirstName(String firstName) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (firstName != null) {
                return criteriaBuilder.equal(root.get("firstName"), firstName);
            } else {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
        };
    }

    public static Specification<Account> hasLastName(String lastName) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (lastName != null) {
                return criteriaBuilder.equal(root.get("lastName"), lastName);
            } else {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
        };
    }

    public static Specification<Account> hasEmail(String email) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (email != null) {
                return criteriaBuilder.equal(root.get("email"), email);
            } else {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
        };
    }
}
