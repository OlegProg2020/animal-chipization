package com.example.animalchipization.service;

import com.example.animalchipization.dto.LocationPointDto;

public interface LocationPointService {

    LocationPointDto findById(Long id);

    LocationPointDto save(LocationPointDto locationPointDto);

    LocationPointDto update(LocationPointDto locationPointDto);

    void deleteById(Long id);

}
