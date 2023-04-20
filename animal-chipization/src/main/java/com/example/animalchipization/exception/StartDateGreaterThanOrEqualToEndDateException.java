package com.example.animalchipization.exception;

import jakarta.validation.ValidationException;

public class StartDateGreaterThanOrEqualToEndDateException extends ValidationException {

    public StartDateGreaterThanOrEqualToEndDateException() {
        super();
    }

}
