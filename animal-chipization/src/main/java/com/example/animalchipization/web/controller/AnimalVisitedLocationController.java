package com.example.animalchipization.web.controller;

import com.example.animalchipization.dto.AnimalVisitedLocationDto;
import com.example.animalchipization.service.AnimalVisitedLocationService;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping(path = "/animals/{animalId}/locations", produces = "application/json")
@Validated
public class AnimalVisitedLocationController {

    private final AnimalVisitedLocationService animalVisitedLocationService;

    @Autowired
    public AnimalVisitedLocationController(AnimalVisitedLocationService animalVisitedLocationService) {
        this.animalVisitedLocationService = animalVisitedLocationService;
    }

    @GetMapping
    public ResponseEntity<Collection<AnimalVisitedLocationDto>> searchForAnimalVisitedLocations(
            @PathVariable("animalId") @Min(1) Long animalId,
            @RequestParam(name = "startDateTime", required = false) ZonedDateTime startDateTime,
            @RequestParam(name = "endDateTime", required = false) ZonedDateTime endDateTime,
            @RequestParam(name = "from", required = false, defaultValue = "0") @Min(0) Integer from,
            @RequestParam(name = "size", required = false, defaultValue = "10") @Min(1) Integer size) {

        Collection<AnimalVisitedLocationDto> animalVisitedLocationsDto = animalVisitedLocationService
                .searchForAnimalVisitedLocations(animalId, startDateTime, endDateTime, from, size);
        return new ResponseEntity<>(animalVisitedLocationsDto, HttpStatus.valueOf(200));
    }

    @PostMapping("/{pointId}")
    @PreAuthorize("hasAnyRole({'ADMIN', 'CHIPPER'})")
    public ResponseEntity<AnimalVisitedLocationDto> addAnimalVisitedLocation(
            @PathVariable("animalId") @Min(1) Long animalId,
            @PathVariable("pointId") @Min(1) Long pointId) {

        AnimalVisitedLocationDto animalVisitedLocation = animalVisitedLocationService
                .addAnimalVisitedLocation(animalId, pointId);
        return new ResponseEntity<>(animalVisitedLocation, HttpStatus.valueOf(201));
    }

    @PutMapping(consumes = "application/json")
    @PreAuthorize("hasAnyRole({'ADMIN', 'CHIPPER'})")
    public ResponseEntity<AnimalVisitedLocationDto> updateAnimalVisitedLocation(
            @PathVariable("animalId") @Min(1) Long animalId,
            @RequestBody Map<String, @Min(1) Long> request) {

        Long visitedLocationPointId = request.get("visitedLocationPointId");
        Long locationPointId = request.get("locationPointId");

        AnimalVisitedLocationDto animalVisitedLocation = animalVisitedLocationService
                .updateAnimalVisitedLocation(animalId, visitedLocationPointId, locationPointId);
        return new ResponseEntity<>(animalVisitedLocation, HttpStatus.valueOf(200));
    }

    @DeleteMapping("/{pointId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAnimalVisitedLocationById(@PathVariable("animalId") @Min(1) Long animalId,
                                                @PathVariable("pointId") @Min(1) Long pointId) {
        animalVisitedLocationService.deleteAnimalVisitedLocation(animalId, pointId);
    }

}
