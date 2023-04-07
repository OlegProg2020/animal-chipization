package com.example.animalchipization.web.controller;

import com.example.animalchipization.entity.Animal;
import com.example.animalchipization.entity.enums.Gender;
import com.example.animalchipization.entity.enums.LifeStatus;
import com.example.animalchipization.mapper.Mapper;
import com.example.animalchipization.service.AnimalService;
import com.example.animalchipization.web.dto.AnimalDto;
import com.example.animalchipization.web.form.AnimalTypePutForm;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;

@RestController
@RequestMapping(path = "/animals", produces = "application/json")
@Validated
public class AnimalController {

    private final AnimalService animalService;
    private final Mapper<Animal, AnimalDto> mapper;

    @Autowired
    public AnimalController(AnimalService animalService,
                            Mapper<Animal, AnimalDto> mapper) {
        this.animalService = animalService;
        this.mapper = mapper;
    }

    @GetMapping("/{animalId}")
    public ResponseEntity<Animal> animalById(@PathVariable("animalId") @Min(1) Long animalId) {
        return new ResponseEntity<>(animalService.findById(animalId), HttpStatus.valueOf(200));
    }

    @GetMapping("/search")
    public ResponseEntity<Iterable<Animal>> searchForAnimals(
            @RequestParam(name = "startDateTime", required = false) ZonedDateTime startDateTime,
            @RequestParam(name = "endDateTime", required = false) ZonedDateTime endDateTime,
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

    @PutMapping(path = "/{animalId}", consumes = "application/json")
    public ResponseEntity<Animal> updateAnimal(@PathVariable("animalId") @Min(1) Long animalId,
                                               @RequestBody @Valid AnimalPutForm animalPutForm) {
        Animal newAnimalDetails = animalPutFormToAnimalConverter.convert(animalPutForm);
        return new ResponseEntity<>(animalService.updateAnimal(animalId, newAnimalDetails),
                HttpStatus.valueOf(200));
    }

    @DeleteMapping("/{animalId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAnimalById(@PathVariable("animalId") @Min(1) Long animalId) {
        animalService.deleteAnimalById(animalId);
    }

    @PostMapping("/{animalId}/types/{typeId}")
    public ResponseEntity<Animal> addTypeToAnimal(@PathVariable("animalId") @Min(1) Long animalId,
                                                  @PathVariable("typeId") @Min(1) Long typeId) {
        return new ResponseEntity<>(animalService.addTypeToAnimal(animalId, typeId), HttpStatus.valueOf(201));
    }

    @PutMapping(path = "/{animalId}/types", consumes = "application/json")
    public ResponseEntity<Animal> updateTypeOfAnimal(@PathVariable("animalId") @Min(1) Long animalId,
                                                     @RequestBody @Valid AnimalTypePutForm animalTypePutForm) {
        Animal animal = animalService.updateTypeOfAnimal(animalId, animalTypePutForm.getOldTypeId(),
                animalTypePutForm.getNewTypeId());
        return new ResponseEntity<>(animal, HttpStatus.valueOf(200));
    }

    @DeleteMapping("/{animalId}/types/{typeId}")
    public ResponseEntity<Animal> deleteTypeOfAnimal(@PathVariable("animalId") @Min(1) Long animalId,
                                                     @PathVariable("typeId") @Min(1) Long typeId) {
        return new ResponseEntity<>(animalService.deleteTypeOfAnimal(animalId, typeId),
                HttpStatus.valueOf(200));
    }

}
