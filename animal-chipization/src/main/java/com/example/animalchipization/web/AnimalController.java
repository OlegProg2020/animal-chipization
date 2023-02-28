package com.example.animalchipization.web;

import com.example.animalchipization.models.Animal;
import com.example.animalchipization.data.AnimalRepository;

import static com.example.animalchipization.data.AnimalSpecification.*;

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
            @RequestParam(name = "startDateTime", required = false) LocalDateTime startDateTime,
            @RequestParam(name = "endDateTime", required = false) LocalDateTime endDateTime,
            @RequestParam(name = "chipperId", required = false) Long chipperId,
            @RequestParam(name = "chippingLocationId", required = false) Long chippingLocationId,
            @RequestParam(name = "lifeStatus", required = false) String lifeStatus,
            @RequestParam(name = "gender", required = false) String gender,
            @RequestParam(name = "from", required = false, defaultValue = "0") Integer from,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {

        if (from < 0 || size <= 0 || chipperId <= 0 || chippingLocationId <= 0) {
            return new ResponseEntity<>(null, HttpStatus.valueOf(400));
        } else {
            PageRequest pageRequest = PageRequest.of(from, size, Sort.by("id").ascending());
            Specification<Animal> specifications = Specification.where(
                    hasChippingDateTimeGreaterThanOrEqualTo(startDateTime)
                            .and(hasChippingDateTimeLessThanOrEqualTo(endDateTime))
                            .and(hasChipperId(chipperId))
                            .and(hasChippingLocationId(chippingLocationId))
                            .and(hasLifeStatus(lifeStatus))
                            .and(hasGender(gender))
            );
            Iterable<Animal> animals = animalRepository.findAll(specifications, pageRequest);
            return new ResponseEntity<>(animals, HttpStatus.valueOf(200));
        }
    }


}
