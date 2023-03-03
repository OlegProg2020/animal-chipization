package com.example.animalchipization.web.controllers;

import com.example.animalchipization.data.repositories.AnimalRepository;
import com.example.animalchipization.models.Animal;
import com.example.animalchipization.services.annotations.CorrectGender;
import com.example.animalchipization.services.annotations.CorrectLifeStatus;
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
import java.util.Optional;

import static com.example.animalchipization.data.specifications.AnimalSpecification.*;

@RestController
@RequestMapping(path = "/animals", produces = "application/json")
@Validated
public class AnimalController {

    private final AnimalRepository animalRepository;

    @Autowired
    public AnimalController(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @GetMapping("/{animalId}")
    public ResponseEntity<Animal> animalById(@PathVariable("animalId") @Min(1) Long animalId) {
        Optional<Animal> optionalAnimal = animalRepository.findById(animalId);
        if (optionalAnimal.isPresent()) {
            return new ResponseEntity<>(optionalAnimal.get(), HttpStatus.valueOf(200));
        } else {
            throw new NoSuchElementException();
        }
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

        PageRequest pageRequest = PageRequest.of(from, size, Sort.by("id").ascending());
        Specification<Animal> specifications = Specification.where(
                hasChippingDateTimeGreaterThanOrEqualTo(startDateTime)
                        .and(hasChippingDateTimeLessThanOrEqualTo(endDateTime))
                        .and(hasChipperId(chipperId))
                        .and(hasChippingLocationId(chippingLocationId))
                        .and(hasLifeStatus(lifeStatus))
                        .and(hasGender(gender))
        );
        Iterable<Animal> animals = animalRepository.findAll(specifications, pageRequest).getContent();
        return new ResponseEntity<>(animals, HttpStatus.valueOf(200));
    }
}
