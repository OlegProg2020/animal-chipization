package com.example.animalchipization.web.controller;

import com.example.animalchipization.model.LocationPoint;
import com.example.animalchipization.service.LocationPointService;
import com.example.animalchipization.web.form.LocationPointForm;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(consumes = "application/json")
    public ResponseEntity<LocationPoint> addLocationPoint(@RequestBody @Valid LocationPointForm locationPointForm) {
        LocationPoint locationPoint = locationPointForm.toLocationPoint();
        return new ResponseEntity<>(locationPointService.addLocationPoint(locationPoint),
                HttpStatus.valueOf(201));
    }

    @PutMapping(path = "/{pointId}", consumes = "application/json")
    public ResponseEntity<LocationPoint> updateLocationPoint(@PathVariable(name = "pointId") Long pointId,
                                                             @RequestBody @Valid LocationPointForm locationPointForm) {
        LocationPoint locationPoint = locationPointForm.toLocationPoint();
        locationPoint.setId(pointId);
        return new ResponseEntity<>(locationPointService.updateLocationPoint(locationPoint),
                HttpStatus.valueOf(200));
    }

    @DeleteMapping(path = "/{pointId}", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void deleteLocationPointById(@PathVariable(name = "pointId") @Min(1) Long pointId) {
        locationPointService.deleteLocationPointById(pointId);
    }
}
