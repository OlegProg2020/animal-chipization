package com.example.animalchipization.web;

import com.example.animalchipization.data.repositories.LocationPointRepository;
import com.example.animalchipization.models.LocationPoint;

import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping(path = "/locations", produces = "application/json")
@Validated
public class LocationPointController {

    private final LocationPointRepository locationPointRepository;

    @Autowired
    public LocationPointController(LocationPointRepository locationPointRepository) {
        this.locationPointRepository = locationPointRepository;
    }

    @GetMapping("/{pointId}")
    public ResponseEntity<LocationPoint> locationPointById(@PathVariable("pointId") @Min(1) Long pointId) {
        Optional<LocationPoint> optionalLocationPoint = locationPointRepository.findById(pointId);
        if (optionalLocationPoint.isPresent()) {
            return new ResponseEntity<>(optionalLocationPoint.get(), HttpStatus.valueOf(200));
        } else {
            throw new NoSuchElementException();
        }
    }
}
