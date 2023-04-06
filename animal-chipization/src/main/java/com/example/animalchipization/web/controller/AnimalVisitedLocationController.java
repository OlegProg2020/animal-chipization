package com.example.animalchipization.web.controller;

import com.example.animalchipization.entity.AnimalVisitedLocation;
import com.example.animalchipization.service.AnimalVisitedLocationService;
import com.example.animalchipization.web.form.AnimalVisitedLocationPutForm;
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
@RequestMapping(path = "/animals/{animalId}/locations", produces = "application/json")
@Validated
public class AnimalVisitedLocationController {

    private final AnimalVisitedLocationService animalVisitedLocationService;
    private final Converter<AnimalVisitedLocationForm, AnimalVisitedLocation> AnimalVisitedLocationFormToAnimalVisitedLocationConverter;

    @Autowired
    public AnimalVisitedLocationController(AnimalVisitedLocationService animalVisitedLocationService,
                                           Converter<AnimalVisitedLocationForm, AnimalVisitedLocation>
                                                   AnimalVisitedLocationFormToAnimalVisitedLocationConverter) {
        this.animalVisitedLocationService = animalVisitedLocationService;
        this.AnimalVisitedLocationFormToAnimalVisitedLocationConverter = AnimalVisitedLocationFormToAnimalVisitedLocationConverter;
    }

    @GetMapping
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

    @PostMapping("/{pointId}")
    public ResponseEntity<AnimalVisitedLocation> addAnimalVisitedLocation(
            @PathVariable("animalId") @Min(1) Long animalId,
            @PathVariable("pointId") @Min(1) Long pointId) {

        AnimalVisitedLocationForm animalVisitedLocationForm = new AnimalVisitedLocationForm(animalId, pointId);
        AnimalVisitedLocation animalVisitedLocation = animalVisitedLocationService.addAnimalVisitedLocation(
                AnimalVisitedLocationFormToAnimalVisitedLocationConverter
                        .convert(animalVisitedLocationForm));
        return new ResponseEntity<>(animalVisitedLocation, HttpStatus.valueOf(201));
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<AnimalVisitedLocation> updateAnimalVisitedLocation(
            @PathVariable("animalId") @Min(1) Long animalId,
            @RequestBody @Valid AnimalVisitedLocationPutForm animalVisitedLocationPutForm) {

        AnimalVisitedLocation animalVisitedLocation = animalVisitedLocationService
                .updateAnimalVisitedLocation(animalId, animalVisitedLocationPutForm.getVisitedLocationPointId(),
                        animalVisitedLocationPutForm.getLocationPointId());
        return new ResponseEntity<>(animalVisitedLocation, HttpStatus.valueOf(200));
    }

    @DeleteMapping("/{pointId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAnimalVisitedLocationById(@PathVariable("animalId") @Min(1) Long animalId,
                                                @PathVariable("pointId") @Min(1) Long pointId) {
        animalVisitedLocationService.deleteAnimalVisitedLocation(animalId, pointId);
    }

}
