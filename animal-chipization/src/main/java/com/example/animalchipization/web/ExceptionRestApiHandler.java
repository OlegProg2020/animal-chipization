package com.example.animalchipization.web;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

//import org.springframework.web.bind.MethodArgumentNotValidException;
import jakarta.validation.ConstraintViolationException;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ExceptionRestApiHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> onConstraintViolationException(ConstraintViolationException exception) {
        return new ResponseEntity<>(HttpStatus.valueOf(400));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> onConstraintViolationException(NoSuchElementException exception) {
        return new ResponseEntity<>(HttpStatus.valueOf(404));
    }
}
