package com.example.animalchipization.util;

import com.example.animalchipization.exception.AccountWithSuchEmailAlreadyExistsException;
import com.example.animalchipization.exception.AnimalTypeWithSuchTypeAlreadyExistsException;
import com.example.animalchipization.exception.DuplicateCollectionItemException;
import com.example.animalchipization.exception.LocationPointWithSuchCoordinatesAlreadyExistsException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> onConstraintViolationException(ConstraintViolationException exception) {
        return new ResponseEntity<>(HttpStatus.valueOf(400));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> onMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return new ResponseEntity<>(HttpStatus.valueOf(400));
    }
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> onValidationException(ValidationException exception) {
        return new ResponseEntity<>(HttpStatus.valueOf(400));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> onConstraintViolationException(NoSuchElementException exception) {
        return new ResponseEntity<>(HttpStatus.valueOf(404));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> onUsernameNotFoundException(UsernameNotFoundException exception) {
        return new ResponseEntity<>(HttpStatus.valueOf(401));
    }

    @ExceptionHandler(AccountWithSuchEmailAlreadyExistsException.class)
    public ResponseEntity<?> onAccountWithThisEmailAlreadyExistsException(
            AccountWithSuchEmailAlreadyExistsException exception) {

        return new ResponseEntity<>(HttpStatus.valueOf(409));
    }

    @ExceptionHandler(DuplicateCollectionItemException.class)
    public ResponseEntity<?> onDuplicateItemException(DuplicateCollectionItemException exception) {
        return new ResponseEntity<>(HttpStatus.valueOf(409));
    }

    @ExceptionHandler(AnimalTypeWithSuchTypeAlreadyExistsException.class)
    public ResponseEntity<?> onAnimalTypeWithThisTypeAlreadyExistsException(
            AnimalTypeWithSuchTypeAlreadyExistsException exception) {

        return new ResponseEntity<>(HttpStatus.valueOf(409));
    }

    @ExceptionHandler(LocationPointWithSuchCoordinatesAlreadyExistsException.class)
    public ResponseEntity<?> onLocationPointWithThisCoordinatesAlreadyExistsException(
            LocationPointWithSuchCoordinatesAlreadyExistsException exception) {

        return new ResponseEntity<>(HttpStatus.valueOf(409));
    }

}
