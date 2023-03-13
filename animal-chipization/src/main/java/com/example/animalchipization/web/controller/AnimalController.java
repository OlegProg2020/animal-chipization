package com.example.animalchipization.web.controller;

import com.example.animalchipization.model.Animal;
import com.example.animalchipization.model.enums.Gender;
import com.example.animalchipization.model.enums.LifeStatus;
import com.example.animalchipization.service.AnimalService;
import com.example.animalchipization.web.form.AnimalForm;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping(path = "/animals", produces = "application/json")
@Validated
public class AnimalController {

    private final AnimalService animalService;
    private Converter<AnimalForm, Animal> animalFormToAnimalConverter;

    @Autowired
    public AnimalController(AnimalService animalService, Converter<AnimalForm, Animal> animalFormToAnimalConverter) {
        this.animalService = animalService;
        this.animalFormToAnimalConverter = animalFormToAnimalConverter;
    }

    @GetMapping("/{animalId}")
    public ResponseEntity<Animal> animalById(@PathVariable("animalId") @Min(1) Long animalId) {
        return new ResponseEntity<>(animalService.findAnimalById(animalId), HttpStatus.valueOf(200));
    }

    @GetMapping("/search")
    public ResponseEntity<Iterable<Animal>> searchForAnimals(
            @RequestParam(name = "startDateTime", required = false) LocalDateTime startDateTime,
            @RequestParam(name = "endDateTime", required = false) LocalDateTime endDateTime,
            @RequestParam(name = "chipperId", required = false) @Min(1) Long chipperId,
            @RequestParam(name = "chippingLocationId", required = false) @Min(1) Long chippingLocationId,
            @RequestParam(name = "lifeStatus", required = false) LifeStatus lifeStatus,
            @RequestParam(name = "gender", required = false) Gender gender,
            @RequestParam(name = "from", required = false, defaultValue = "0") @Min(0) Integer from,
            @RequestParam(name = "size", required = false, defaultValue = "10") @Min(1) Integer size) {

        Iterable<Animal> animals = animalService.searchForAnimals(startDateTime, endDateTime, chipperId,
                chippingLocationId, lifeStatus, gender, from, size);
        return new ResponseEntity<>(animals, HttpStatus.valueOf(200));
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Animal> addAnimal(@RequestBody @Valid AnimalForm animalForm) {
        Animal animal = animalFormToAnimalConverter.convert(animalForm);
        return new ResponseEntity<>(animalService.addAnimal(animal), HttpStatus.valueOf(201));
    }

}
