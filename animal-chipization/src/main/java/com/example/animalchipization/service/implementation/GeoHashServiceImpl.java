package com.example.animalchipization.service.implementation;

import ch.hsr.geohash.GeoHash;
import com.example.animalchipization.service.GeoHashService;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class GeoHashServiceImpl implements GeoHashService {

    @Override
    public String encodeWithGeoHash(Double latitude, Double longitude) {
        return GeoHash.geoHashStringWithCharacterPrecision(latitude, longitude, 12);
    }

    @Override
    public String encodeWithGeoHashV2(Double latitude, Double longitude) {
        String base32String = GeoHash.geoHashStringWithCharacterPrecision(latitude, longitude, 12);
        return Base64.getEncoder().encodeToString(base32String.getBytes());
    }

}
