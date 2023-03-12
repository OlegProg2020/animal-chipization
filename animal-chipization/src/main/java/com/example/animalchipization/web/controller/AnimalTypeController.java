package com.example.animalchipization.web.controller;

import com.example.animalchipization.model.AnimalType;
import com.example.animalchipization.service.AnimalTypeService;
import com.example.animalchipization.web.form.AnimalTypeForm;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(consumes = "application/json")
    public ResponseEntity<AnimalType> addAnimalType(@RequestBody @Valid AnimalTypeForm animalTypeForm) {
        return new ResponseEntity<>(animalTypeService.addAnimalType(animalTypeForm), HttpStatus.valueOf(201));
    }

    @PutMapping(path = "/{typeId}", consumes = "application/json")
    public ResponseEntity<AnimalType> updateAnimalTypeById(@PathVariable("typeId") @Min(1) Long typeId,
                                                           @RequestBody @Valid AnimalTypeForm animalTypeForm) {
        AnimalType updatedAnimalType = animalTypeService.updateAnimalTypeById(typeId, animalTypeForm);
        return new ResponseEntity<>(updatedAnimalType, HttpStatus.valueOf(200));
    }

    @DeleteMapping(path = "/{typeId}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteAnimalTypeById(@PathVariable("typeId") @Min(1) Long typeId) {
        animalTypeService.deleteAnimalTypeById(typeId);
    }

}
