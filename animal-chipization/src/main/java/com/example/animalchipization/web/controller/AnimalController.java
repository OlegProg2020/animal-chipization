package com.example.animalchipization.web.controller;

import com.example.animalchipization.dto.AnimalDto;
import com.example.animalchipization.entity.enums.Gender;
import com.example.animalchipization.entity.enums.LifeStatus;
import com.example.animalchipization.service.AnimalService;
import jakarta.validation.Valid;
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
@RequestMapping(path = "/animals", produces = "application/json")
@Validated
public class AnimalController {

    private final AnimalService animalService;

    @Autowired
    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @GetMapping("/{animalId}")
    public ResponseEntity<AnimalDto> animalById(@PathVariable("animalId") @Min(1) Long animalId) {
        return new ResponseEntity<>(animalService.findById(animalId), HttpStatus.valueOf(200));
    }

    @GetMapping("/search")
    public ResponseEntity<Collection<AnimalDto>> searchForAnimals(
            @RequestParam(name = "startDateTime", required = false) ZonedDateTime startDateTime,
            @RequestParam(name = "endDateTime", required = false) ZonedDateTime endDateTime,
            @RequestParam(name = "chipperId", required = false) @Min(1) Long chipperId,
            @RequestParam(name = "chippingLocationId", required = false) @Min(1) Long chippingLocationId,
            @RequestParam(name = "lifeStatus", required = false) LifeStatus lifeStatus,
            @RequestParam(name = "gender", required = false) Gender gender,
            @RequestParam(name = "from", required = false, defaultValue = "0") @Min(0) Integer from,
            @RequestParam(name = "size", required = false, defaultValue = "10") @Min(1) Integer size) {

        Collection<AnimalDto> animalsDto = animalService.searchForAnimals(startDateTime, endDateTime, chipperId,
                chippingLocationId, lifeStatus, gender, from, size);
        return new ResponseEntity<>(animalsDto, HttpStatus.valueOf(200));
    }

    @PostMapping(consumes = "application/json")
    @PreAuthorize("hasAnyRole({'ADMIN', 'CHIPPER'})")
    public ResponseEntity<AnimalDto> addAnimal(@RequestBody @Valid AnimalDto animalDto) {
        return new ResponseEntity<>(animalService.addAnimal(animalDto), HttpStatus.valueOf(201));
    }

    @PutMapping(path = "/{animalId}", consumes = "application/json")
    @PreAuthorize("hasAnyRole({'ADMIN', 'CHIPPER'})")
    public ResponseEntity<AnimalDto> updateAnimal(@PathVariable("animalId") @Min(1) Long animalId,
                                                  @RequestBody @Valid AnimalDto updatedAnimalDto) {
        return new ResponseEntity<>(animalService.updateAnimal(animalId, updatedAnimalDto),
                HttpStatus.valueOf(200));
    }

    @DeleteMapping("/{animalId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAnimalById(@PathVariable("animalId") @Min(1) Long animalId) {
        animalService.deleteAnimalById(animalId);
    }

    @PostMapping("/{animalId}/types/{typeId}")
    @PreAuthorize("hasAnyRole({'ADMIN', 'CHIPPER'})")
    public ResponseEntity<AnimalDto> addTypeToAnimal(@PathVariable("animalId") @Min(1) Long animalId,
                                                     @PathVariable("typeId") @Min(1) Long typeId) {

        return new ResponseEntity<>(animalService.addTypeToAnimal(animalId, typeId),
                HttpStatus.valueOf(201));
    }

    @PutMapping(path = "/{animalId}/types", consumes = "application/json")
    @PreAuthorize("hasAnyRole({'ADMIN', 'CHIPPER'})")
    public ResponseEntity<AnimalDto> updateTypeOfAnimal(@PathVariable("animalId") @Min(1) Long animalId,
                                                        @RequestBody Map<String, @Min(1) Long> request) {

        Long oldTypeId = request.get("oldTypeId");
        Long newTypeId = request.get("newTypeId");
        return new ResponseEntity<>(animalService.updateTypeOfAnimal(animalId, oldTypeId, newTypeId),
                HttpStatus.valueOf(200));
    }

    @DeleteMapping("/{animalId}/types/{typeId}")
    @PreAuthorize("hasAnyRole({'ADMIN', 'CHIPPER'})")
    public ResponseEntity<AnimalDto> deleteTypeOfAnimal(@PathVariable("animalId") @Min(1) Long animalId,
                                                        @PathVariable("typeId") @Min(1) Long typeId) {
        return new ResponseEntity<>(animalService.deleteTypeOfAnimal(animalId, typeId),
                HttpStatus.valueOf(200));
    }

}
