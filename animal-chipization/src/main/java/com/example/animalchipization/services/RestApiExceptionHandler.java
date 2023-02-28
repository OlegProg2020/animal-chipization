package com.example.animalchipization.services;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

//import org.springframework.web.bind.MethodArgumentNotValidException;
import jakarta.validation.ConstraintViolationException;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class RestApiExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> onConstraintViolationException(ConstraintViolationException exception) {
        return new ResponseEntity<>(HttpStatus.valueOf(400));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> onConstraintViolationException(NoSuchElementException exception) {
        return new ResponseEntity<>(HttpStatus.valueOf(404));
    }
}
