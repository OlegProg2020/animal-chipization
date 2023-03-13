package com.example.animalchipization.util;

import com.example.animalchipization.exception.AccountWithThisEmailAlreadyExistsException;
import com.example.animalchipization.exception.AnimalTypeWithThisTypeAlreadyExistsException;
import com.example.animalchipization.exception.DuplicateCollectionItemException;
import com.example.animalchipization.exception.LocationPointWithThisCoordinatesAlreadyExistsException;
import jakarta.validation.ConstraintViolationException;
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

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> onConstraintViolationException(NoSuchElementException exception) {
        return new ResponseEntity<>(HttpStatus.valueOf(404));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> onUsernameNotFoundException(UsernameNotFoundException exception) {
        return new ResponseEntity<>(HttpStatus.valueOf(401));
    }

    @ExceptionHandler(AccountWithThisEmailAlreadyExistsException.class)
    public ResponseEntity<?> onAccountWithThisEmailAlreadyExistsException(
            AccountWithThisEmailAlreadyExistsException exception) {

        return new ResponseEntity<>(HttpStatus.valueOf(409));
    }

    @ExceptionHandler(DuplicateCollectionItemException.class)
    public ResponseEntity<?> onDuplicateItemException(DuplicateCollectionItemException exception) {
        return new ResponseEntity<>(HttpStatus.valueOf(409));
    }

    @ExceptionHandler(AnimalTypeWithThisTypeAlreadyExistsException.class)
    public ResponseEntity<?> onAnimalTypeWithThisTypeAlreadyExistsException(
            AnimalTypeWithThisTypeAlreadyExistsException exception) {

        return new ResponseEntity<>(HttpStatus.valueOf(409));
    }

    @ExceptionHandler(LocationPointWithThisCoordinatesAlreadyExistsException.class)
    public ResponseEntity<?> onLocationPointWithThisCoordinatesAlreadyExistsException(
            LocationPointWithThisCoordinatesAlreadyExistsException exception) {

        return new ResponseEntity<>(HttpStatus.valueOf(409));
    }

}
