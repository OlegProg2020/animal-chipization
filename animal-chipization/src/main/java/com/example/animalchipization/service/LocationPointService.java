package com.example.animalchipization.service;

import com.example.animalchipization.entity.LocationPoint;
import com.example.animalchipization.web.dto.LocationPointDto;

public interface LocationPointService {

    LocationPointDto findLocationPointById(Long pointId);

    LocationPointDto addLocationPoint(LocationPointDto locationPoint);

    LocationPointDto updateLocationPoint(LocationPointDto locationPoint);

    void deleteLocationPointById(Long id);

}
