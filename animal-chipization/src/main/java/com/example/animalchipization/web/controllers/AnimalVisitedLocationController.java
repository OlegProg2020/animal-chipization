package com.example.animalchipization.web.controllers;

import com.example.animalchipization.data.repositories.AnimalRepository;
import com.example.animalchipization.data.repositories.AnimalVisitedLocationRepository;
import com.example.animalchipization.models.AnimalVisitedLocation;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static com.example.animalchipization.data.specifications.AnimalVisitedLocationSpecification.*;

@RestController
@RequestMapping(path = "/animals", produces = "application/json")
@Validated
public class AnimalVisitedLocationController {

    private final AnimalRepository animalRepository;
    private final AnimalVisitedLocationRepository animalVisitedLocationRepository;

    @Autowired
    public AnimalVisitedLocationController(AnimalRepository animalRepository,
                                           AnimalVisitedLocationRepository animalVisitedLocationRepository) {
        this.animalRepository = animalRepository;
        this.animalVisitedLocationRepository = animalVisitedLocationRepository;
    }

    @GetMapping("/{animalId}/locations")
    public ResponseEntity<Iterable<AnimalVisitedLocation>> searchForAnimalVisitedLocations(
            @PathVariable("animalId") @Min(1) Long animalId,
            @RequestParam(name = "startDateTime", required = false) LocalDateTime startDateTime,
            @RequestParam(name = "endDateTime", required = false) LocalDateTime endDateTime,
            @RequestParam(name = "from", required = false, defaultValue = "0") @Min(0) Integer from,
            @RequestParam(name = "size", required = false, defaultValue = "10") @Min(1) Integer size) {

        if (animalRepository.existsById(animalId)) {
            PageRequest pageRequest = PageRequest.of(from, size,
                    Sort.by("dateTimeOfVisitLocationPoint").ascending());
            Specification<AnimalVisitedLocation> specifications = Specification.where(
                    hasAnimalId(animalId)
                            .and(hasDateTimeOfVisitLocationPointGreaterThanOrEqualTo(startDateTime))
                            .and(hasDateTimeOfVisitLocationPointLessThanOrEqualTo(endDateTime))
            );
            Iterable<AnimalVisitedLocation> animalVisitedLocations = animalVisitedLocationRepository
                    .findAll(specifications, pageRequest).getContent();
            return new ResponseEntity<>(animalVisitedLocations, HttpStatus.valueOf(200));
        } else {
            throw new NoSuchElementException();
        }
    }

}
