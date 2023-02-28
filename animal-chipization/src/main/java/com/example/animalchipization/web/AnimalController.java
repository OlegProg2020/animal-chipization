package com.example.animalchipization.web;

import com.example.animalchipization.models.Animal;
import com.example.animalchipization.data.repositories.AnimalRepository;

import static com.example.animalchipization.data.specifications.AnimalSpecification.*;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

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
            @RequestParam(name = "startDateTime") LocalDateTime startDateTime,
            @RequestParam(name = "endDateTime") LocalDateTime endDateTime,
            @RequestParam(name = "chipperId") @Min(1) Long chipperId,
            @RequestParam(name = "chippingLocationId") @Min(1) Long chippingLocationId,
            @RequestParam(name = "lifeStatus") @CorrectLifeStatus String lifeStatus,
            @RequestParam(name = "gender") @CorrectGender String gender,
            @RequestParam(name = "from", defaultValue = "0") @Min(0) Integer from,
            @RequestParam(name = "size", defaultValue = "10") @Min(1) Integer size) {

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
