package com.example.animalchipization.exception;

import jakarta.validation.ValidationException;

public class AnimalIsAlreadyAtThisPointException extends ValidationException {
    public AnimalIsAlreadyAtThisPointException() {
        super();
    }
}
