package com.example.animalchipization.web.controller;

import com.example.animalchipization.dto.LocationPointDto;
import com.example.animalchipization.service.LocationPointService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<LocationPointDto> findLocationPointById(@PathVariable("pointId") @Min(1) Long pointId) {
        return new ResponseEntity<>(locationPointService.findById(pointId),
                HttpStatus.valueOf(200));
    }

    @GetMapping
    public ResponseEntity<Long> findLocationPointByLatitudeAndLongitude(
            @RequestParam("latitude") @DecimalMin(value = "-90") @DecimalMax(value = "90") Double latitude,
            @RequestParam("longitude") @DecimalMin(value = "-180") @DecimalMax(value = "180") Double longitude) {

        Long locationId = locationPointService.findByLatitudeAndLongitude(latitude, longitude).getId();
        return new ResponseEntity<>(locationId,
                HttpStatus.valueOf(200));
    }

    @PostMapping(consumes = "application/json")
    @PreAuthorize("hasAnyRole({'ADMIN', 'CHIPPER'})")
    public ResponseEntity<LocationPointDto> addLocationPoint(
            @RequestBody @Valid LocationPointDto locationPointDto) {

        return new ResponseEntity<>(locationPointService.save(locationPointDto),
                HttpStatus.valueOf(201));
    }

    @PutMapping(path = "/{pointId}", consumes = "application/json")
    @PreAuthorize("hasAnyRole({'ADMIN', 'CHIPPER'})")
    public ResponseEntity<LocationPointDto> updateLocationPoint(
            @PathVariable(name = "pointId") @Min(1) Long pointId,
            @RequestBody @Valid LocationPointDto locationPointDto) {

        locationPointDto.setId(pointId);
        return new ResponseEntity<>(locationPointService.update(locationPointDto),
                HttpStatus.valueOf(200));
    }

    @DeleteMapping(path = "/{pointId}", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteLocationPointById(@PathVariable(name = "pointId") @Min(1) Long pointId) {
        locationPointService.deleteById(pointId);
    }
}
