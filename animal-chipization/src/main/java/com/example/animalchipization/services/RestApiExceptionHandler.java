package com.example.animalchipization.services;

import com.example.animalchipization.exceptions.AccountWithThisEmailAlreadyExistsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;
import jakarta.validation.ConstraintViolationException;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class RestApiExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> onConstraintViolationException(ConstraintViolationException exception) {
        return new ResponseEntity<>(HttpStatus.valueOf(400));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> onMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return new ResponseEntity<>(HttpStatus.valueOf(400));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> onConstraintViolationException(NoSuchElementException exception) {
        return new ResponseEntity<>(HttpStatus.valueOf(404));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> onUsernameNotFoundException(UsernameNotFoundException exception) {
        return new ResponseEntity<>(HttpStatus.valueOf(403));
    }

    @ExceptionHandler(AccountWithThisEmailAlreadyExistsException.class)
    public ResponseEntity<?> onAccountWithThisEmailAlreadyExists(AccountWithThisEmailAlreadyExistsException exception) {
        return new ResponseEntity<>(HttpStatus.valueOf(409));
    }
}
