package com.example.animalchipization.web;

import com.example.animalchipization.data.AnimalTypeRepository;
import com.example.animalchipization.models.AnimalType;
import com.example.animalchipization.models.LocationPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(path = "/animals/types", produces = "application/json")
public class AnimalTypeController {

    private final AnimalTypeRepository animalTypeRepository;

    @Autowired
    public AnimalTypeController(AnimalTypeRepository animalTypeRepository) {
        this.animalTypeRepository = animalTypeRepository;
    }

    @GetMapping("/{typeId}")
    public ResponseEntity<AnimalType> animalTypeById(@PathVariable("typeId") Long typeId) {
        ResponseEntity<AnimalType> response;
        if (typeId <= 0) {
            response = new ResponseEntity<>(null, HttpStatus.valueOf(400));
        } else {
            Optional<AnimalType> animalType = animalTypeRepository.findById(typeId);
            if (animalType.isPresent()) {
                response = new ResponseEntity<>(animalType.get(), HttpStatus.valueOf(200));
            } else {
                response = new ResponseEntity<>(null, HttpStatus.valueOf(404));
            }
        }
        return response;
    }

}
