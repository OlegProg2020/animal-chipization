package com.example.animalchipization.web.controllers;

import com.example.animalchipization.data.repositories.AnimalTypeRepository;
import com.example.animalchipization.models.AnimalType;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping(path = "/animals/types", produces = "application/json")
@Validated
public class AnimalTypeController {

    private final AnimalTypeRepository animalTypeRepository;

    @Autowired
    public AnimalTypeController(AnimalTypeRepository animalTypeRepository) {
        this.animalTypeRepository = animalTypeRepository;
    }

    @GetMapping("/{typeId}")
    public ResponseEntity<AnimalType> animalTypeById(@PathVariable("typeId") @Min(1) Long typeId) {
        Optional<AnimalType> optionalAnimalType = animalTypeRepository.findById(typeId);
        if (optionalAnimalType.isPresent()) {
            return new ResponseEntity<>(optionalAnimalType.get(), HttpStatus.valueOf(200));
        } else {
            throw new NoSuchElementException();
        }
    }

}
