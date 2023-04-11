package com.example.animalchipization.data.specification;

import com.example.animalchipization.entity.Account;
import org.springframework.data.jpa.domain.Specification;

public class AccountSpecification {

    public static Specification<Account> firstNameLike(String firstName) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (firstName != null) {
                return criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")),
                        ("%" + firstName + "%").toLowerCase());
            } else {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
        };
    }

    public static Specification<Account> lastNameLike(String lastName) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (lastName != null) {
                return criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")),
                        ("%" + lastName + "%").toLowerCase());
            } else {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
        };
    }

    public static Specification<Account> emailLike(String email) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (email != null) {
                return criteriaBuilder.like(criteriaBuilder.lower(root.get("email")),
                        ("%" + email + "%").toLowerCase());
            } else {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
        };
    }
}
