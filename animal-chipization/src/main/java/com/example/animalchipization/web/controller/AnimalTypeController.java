package com.example.animalchipization.web.controller;

import com.example.animalchipization.dto.AnimalTypeDto;
import com.example.animalchipization.service.AnimalTypeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/animals/types", produces = "application/json")
@Validated
public class AnimalTypeController {

    private final AnimalTypeService animalTypeService;

    @Autowired
    public AnimalTypeController(AnimalTypeService animalTypeService) {
        this.animalTypeService = animalTypeService;
    }

    @GetMapping("/{typeId}")
    public ResponseEntity<AnimalTypeDto> findAnimalTypeById(@PathVariable("typeId") @Min(1) Long typeId) {
        return new ResponseEntity<>(animalTypeService.findAnimalTypeById(typeId),
                HttpStatus.valueOf(200));
    }

    @PostMapping(consumes = "application/json")
    @PreAuthorize("hasAnyRole({'ADMIN', 'CHIPPER'})")
    public ResponseEntity<AnimalTypeDto> addAnimalType(@RequestBody @Valid AnimalTypeDto animalTypeDto) {
        return new ResponseEntity<>(animalTypeService.addAnimalType(animalTypeDto),
                HttpStatus.valueOf(201));
    }

    @PutMapping(path = "/{typeId}", consumes = "application/json")
    @PreAuthorize("hasAnyRole({'ADMIN', 'CHIPPER'})")
    public ResponseEntity<AnimalTypeDto> updateAnimalType(@PathVariable("typeId") @Min(1) Long typeId,
                                                          @RequestBody @Valid AnimalTypeDto animalTypeDto) {
        animalTypeDto.setId(typeId);
        return new ResponseEntity<>(animalTypeService.updateAnimalType(animalTypeDto),
                HttpStatus.valueOf(200));
    }

    @DeleteMapping(path = "/{typeId}")
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAnimalTypeById(@PathVariable("typeId") @Min(1) Long typeId) {
        animalTypeService.deleteAnimalTypeById(typeId);
    }

}
