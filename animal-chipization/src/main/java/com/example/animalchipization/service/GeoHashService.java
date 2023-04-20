package com.example.animalchipization.service;

public interface GeoHashService {

    String encodeWithGeoHash(Double latitude, Double longitude);

    String encodeWithGeoHashV2(Double latitude, Double longitude);

}
