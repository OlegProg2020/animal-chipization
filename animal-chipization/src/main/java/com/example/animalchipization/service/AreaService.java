package com.example.animalchipization.service;

import com.example.animalchipization.dto.AreaDto;

public interface AreaService {

    AreaDto findById(Long id);
    AreaDto save(AreaDto areaDto);
    AreaDto update(AreaDto areaDto);
    void deleteById(Long id);

}
