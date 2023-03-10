package com.example.animalchipization.web.controller;

import com.example.animalchipization.model.AnimalVisitedLocation;
import com.example.animalchipization.service.AnimalVisitedLocationService;
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
public class AnimalVisitedLocationController {

    private final AnimalVisitedLocationService animalVisitedLocationService;

    @Autowired
    public AnimalVisitedLocationController(AnimalVisitedLocationService animalVisitedLocationService) {
        this.animalVisitedLocationService = animalVisitedLocationService;
    }

    @GetMapping("/{animalId}/locations")
    public ResponseEntity<Iterable<AnimalVisitedLocation>> searchForAnimalVisitedLocations(
            @PathVariable("animalId") @Min(1) Long animalId,
            @RequestParam(name = "startDateTime", required = false) LocalDateTime startDateTime,
            @RequestParam(name = "endDateTime", required = false) LocalDateTime endDateTime,
            @RequestParam(name = "from", required = false, defaultValue = "0") @Min(0) Integer from,
            @RequestParam(name = "size", required = false, defaultValue = "10") @Min(1) Integer size) {

        Iterable<AnimalVisitedLocation> animalVisitedLocations = animalVisitedLocationService
                .searchForAnimalVisitedLocations(animalId, startDateTime, endDateTime, from, size);
        return new ResponseEntity<>(animalVisitedLocations, HttpStatus.valueOf(200));
    }

}
