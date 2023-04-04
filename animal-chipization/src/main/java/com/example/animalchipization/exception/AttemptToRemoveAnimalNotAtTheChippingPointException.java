package com.example.animalchipization.exception;

import jakarta.validation.ValidationException;

public class AttemptToRemoveAnimalNotAtTheChippingPointException extends ValidationException {
    public AttemptToRemoveAnimalNotAtTheChippingPointException() {
        super();
    }
}
