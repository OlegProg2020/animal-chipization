package com.example.animalchipization.exception.handler;

import com.example.animalchipization.exception.*;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import org.springframework.dao.DataIntegrityViolationException;
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
    public ResponseEntity<?> onConstraintViolationException() {
        return new ResponseEntity<>(HttpStatus.valueOf(400));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> onMethodArgumentNotValidException() {
        return new ResponseEntity<>(HttpStatus.valueOf(400));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> onValidationException() {
        return new ResponseEntity<>(HttpStatus.valueOf(400));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> onDataIntegrityViolationException() {
        return new ResponseEntity<>(HttpStatus.valueOf(400));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> onNoSuchElementException() {
        return new ResponseEntity<>(HttpStatus.valueOf(404));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> onUsernameNotFoundException() {
        return new ResponseEntity<>(HttpStatus.valueOf(401));
    }

    @ExceptionHandler(AccountWithSuchEmailAlreadyExistsException.class)
    public ResponseEntity<?> onAccountWithThisEmailAlreadyExistsException() {
        return new ResponseEntity<>(HttpStatus.valueOf(409));
    }

    @ExceptionHandler(AreaWithSuchAreaPointsAlreadyExistsException.class)
    public ResponseEntity<?> onAreaWithSuchAreaPointsAlreadyExistsException() {
        return new ResponseEntity<>(HttpStatus.valueOf(409));
    }

    @ExceptionHandler(DuplicateCollectionItemException.class)
    public ResponseEntity<?> onDuplicateItemException() {
        return new ResponseEntity<>(HttpStatus.valueOf(409));
    }

    @ExceptionHandler(AnimalTypeWithSuchTypeAlreadyExistsException.class)
    public ResponseEntity<?> onAnimalTypeWithThisTypeAlreadyExistsException() {
        return new ResponseEntity<>(HttpStatus.valueOf(409));
    }

    @ExceptionHandler(LocationPointWithSuchCoordinatesAlreadyExistsException.class)
    public ResponseEntity<?> onLocationPointWithSuchCoordinatesAlreadyExistsException() {
        return new ResponseEntity<>(HttpStatus.valueOf(409));
    }

}
