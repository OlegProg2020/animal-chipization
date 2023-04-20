package com.example.animalchipization.exception;

import jakarta.validation.ValidationException;

public class EmptyAnimalTypesException extends ValidationException {

    public EmptyAnimalTypesException() {
        super();
    }
}
