package com.example.animalchipization.web.controller;

import com.example.animalchipization.model.LocationPoint;
import com.example.animalchipization.service.LocationPointService;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/locations", produces = "application/json")
@Validated
public class LocationPointController {

    private final LocationPointService locationPointService;

    @Autowired
    public LocationPointController(LocationPointService locationPointService) {
        this.locationPointService = locationPointService;
    }

    @GetMapping("/{pointId}")
    public ResponseEntity<LocationPoint> findLocationPointById(@PathVariable("pointId") @Min(1) Long pointId) {
        return new ResponseEntity<>(locationPointService.findLocationPointById(pointId),
                HttpStatus.valueOf(200));
    }
}
