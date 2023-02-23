package com.example.animalchipization.web;

import com.example.animalchipization.data.LocationPointRepository;
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
@RequestMapping(path = "/locations", produces = "application/json")
public class LocationPointController {

    private final LocationPointRepository locationPointRepository;

    @Autowired
    public LocationPointController(LocationPointRepository locationPointRepository) {
        this.locationPointRepository = locationPointRepository;
    }

    @GetMapping("/{pointId}")
    public ResponseEntity<LocationPoint> locationPointById(@PathVariable("pointId") Long pointId) {
        ResponseEntity<LocationPoint> response;
        if (pointId <= 0) {
            response = new ResponseEntity<>(null, HttpStatus.valueOf(400));
        } else {
            Optional<LocationPoint> locationPoint = locationPointRepository.findById(pointId);
            if (locationPoint.isPresent()) {
                response = new ResponseEntity<>(locationPoint.get(), HttpStatus.valueOf(200));
            } else {
                response = new ResponseEntity<>(null, HttpStatus.valueOf(404));
            }
        }
        return response;
    }
}
