package com.example.animalchipization.web.controller;

import com.example.animalchipization.model.AnimalType;
import com.example.animalchipization.service.AnimalTypeService;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/animals/types", produces = "application/json")
@Validated
public class AnimalTypeController {

    private final AnimalTypeService animalTypeService;

    @Autowired
    public AnimalTypeController(AnimalTypeService animalTypeService) {
        this.animalTypeService = animalTypeService;
    }

    @GetMapping("/{typeId}")
    public ResponseEntity<AnimalType> findAnimalTypeById(@PathVariable("typeId") @Min(1) Long typeId) {
        return new ResponseEntity<>(animalTypeService.findAnimalTypeById(typeId), HttpStatus.valueOf(200));
    }

}
