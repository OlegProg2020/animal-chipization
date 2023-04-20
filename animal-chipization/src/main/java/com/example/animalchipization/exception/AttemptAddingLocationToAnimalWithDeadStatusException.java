package com.example.animalchipization.exception;

import jakarta.validation.ValidationException;

public class AttemptAddingLocationToAnimalWithDeadStatusException extends ValidationException {
    public AttemptAddingLocationToAnimalWithDeadStatusException() {
        super();
    }
}
