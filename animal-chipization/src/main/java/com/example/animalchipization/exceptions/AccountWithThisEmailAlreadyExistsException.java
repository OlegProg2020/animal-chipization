package com.example.animalchipization.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AccountWithThisEmailAlreadyExistsException extends RuntimeException {
    public AccountWithThisEmailAlreadyExistsException(final String message) {
        super(message);
    }
}
