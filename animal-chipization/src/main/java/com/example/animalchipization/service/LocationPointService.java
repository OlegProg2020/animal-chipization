package com.example.animalchipization.service;

import com.example.animalchipization.model.LocationPoint;

public interface LocationPointService {

    LocationPoint findLocationPointById(Long pointId);

    LocationPoint addLocationPoint(LocationPoint locationPoint);

    LocationPoint updateLocationPoint(LocationPoint locationPoint);

    void deleteLocationPointById(Long id);
}
