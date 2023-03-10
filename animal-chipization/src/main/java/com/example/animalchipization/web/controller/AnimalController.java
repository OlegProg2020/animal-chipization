package com.example.animalchipization.web.controller;

import com.example.animalchipization.model.Animal;
import com.example.animalchipization.service.AnimalService;
import com.example.animalchipization.validation.annotations.CorrectGender;
import com.example.animalchipization.validation.annotations.CorrectLifeStatus;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
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
            @RequestParam(name = "lifeStatus", required = false) @CorrectLifeStatus String lifeStatus,
            @RequestParam(name = "gender", required = false) @CorrectGender String gender,
            @RequestParam(name = "from", required = false, defaultValue = "0") @Min(0) Integer from,
            @RequestParam(name = "size", required = false, defaultValue = "10") @Min(1) Integer size) {

        Iterable<Animal> animals = animalService.searchForAnimals(startDateTime, endDateTime, chipperId,
                chippingLocationId, lifeStatus, gender, from, size);
        return new ResponseEntity<>(animals, HttpStatus.valueOf(200));
    }
}
