package com.example.animalchipization.web;

import com.example.animalchipization.data.AnimalRepository;
import com.example.animalchipization.data.AnimalSpecification;
import com.example.animalchipization.data.SearchCriteria;
import static com.example.animalchipization.data.SearchCriteria.CriteriaOperation.*;
import com.example.animalchipization.models.Animal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping(path = "/animals", produces = "application/json")
public class AnimalController {

    private final AnimalRepository animalRepository;

    @Autowired
    public AnimalController(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @GetMapping("/{animalId}")
    public ResponseEntity<Animal> animalById(@PathVariable("animalId") Long animalId) {
        ResponseEntity<Animal> response;
        if (animalId <= 0) {
            response = new ResponseEntity<>(null, HttpStatus.valueOf(400));
        } else {
            Optional<Animal> animal = animalRepository.findById(animalId);
            if (animal.isPresent()) {
                response = new ResponseEntity<>(animal.get(), HttpStatus.valueOf(200));
            } else {
                response = new ResponseEntity<>(null, HttpStatus.valueOf(404));
            }
        }
        return response;
    }

    @GetMapping("/search")
    public ResponseEntity<Iterable<Animal>> searchAnimals(
            @RequestParam(name = "startDateTime", required = false) LocalDateTime startDateTime,
            @RequestParam(name = "endDateTime", required = false) LocalDateTime endDateTime,
            @RequestParam(name = "chipperId", required = false) Long chipperId,
            @RequestParam(name = "lifeStatus", required = false) String lifeStatus,
            @RequestParam(name = "gender", required = false) String gender,
            @RequestParam(name = "from", required = false, defaultValue = "0") Integer from,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {

        //TODO
        if (from < 0 || size <= 0) {
            return new ResponseEntity<>(HttpStatus.valueOf(400));
        }
        PageRequest pageRequest = PageRequest.of(from, size, Sort.by("id").ascending());
        Page<Animal> animals = animalRepository.findAll(
                Specification.where(
                        new AnimalSpecification(new SearchCriteria<LocalDateTime>("startDateTime", startDateTime, GREATER_OR_EQUAL))
                                .and(new AnimalSpecification(new SearchCriteria<LocalDateTime>("endDateTime", endDateTime, LESS_OR_EQUAL)))
                                .and(new AnimalSpecification(new SearchCriteria<Long>("chipperId", chipperId, EQUALS)))
                                .and(new AnimalSpecification(new SearchCriteria<String>("lifeStatus", lifeStatus, EQUALS)))
                                .and(new AnimalSpecification(new SearchCriteria<String>("gender", gender, EQUALS)))
                                .and(new AnimalSpecification(new SearchCriteria<Long>("chipperId", chipperId, EQUALS)))),
                pageRequest);
        return new ResponseEntity<>(animals, HttpStatus.valueOf(200));
    }

}
