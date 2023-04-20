package com.example.animalchipization.web.controller;

import com.example.animalchipization.service.GeoHashService;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/locations", produces = "application/json")
@Validated
public class GeoHashController {

    private final GeoHashService geoHashService;

    @Autowired
    public GeoHashController(GeoHashService geoHashService) {
        this.geoHashService = geoHashService;
    }

    @GetMapping("/geohash")
    public ResponseEntity<String> encodeWithGeoHash(
            @RequestParam("latitude") @DecimalMin(value = "-90") @DecimalMax(value = "90") Double latitude,
            @RequestParam("longitude") @DecimalMin(value = "-180") @DecimalMax(value = "180") Double longitude) {

        return new ResponseEntity<>(geoHashService.encodeWithGeoHash(latitude, longitude),
                HttpStatus.valueOf(200));
    }

    @GetMapping("/geohashv2")
    public ResponseEntity<String> encodeWithGeoHashV2(
            @RequestParam("latitude") @DecimalMin(value = "-90") @DecimalMax(value = "90") Double latitude,
            @RequestParam("longitude") @DecimalMin(value = "-180") @DecimalMax(value = "180") Double longitude) {

        return new ResponseEntity<>(geoHashService.encodeWithGeoHashV2(latitude, longitude),
                HttpStatus.valueOf(200));
    }

}
