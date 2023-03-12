package com.example.animalchipization.service.implementation;

import com.example.animalchipization.data.repository.LocationPointRepository;
import com.example.animalchipization.model.LocationPoint;
import com.example.animalchipization.service.LocationPointService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class LocationPointServiceImpl implements LocationPointService {

    private final LocationPointRepository locationPointRepository;

    @Autowired
    public LocationPointServiceImpl(LocationPointRepository locationPointRepository) {
        this.locationPointRepository = locationPointRepository;
    }

    @Override
    public LocationPoint findLocationPointById(Long pointId) {
        Optional<LocationPoint> optionalLocationPoint = locationPointRepository.findById(pointId);
        if (optionalLocationPoint.isPresent()) {
            return optionalLocationPoint.get();
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public LocationPoint addLocationPoint(@Valid LocationPoint locationPoint) {
        return locationPointRepository.save(locationPoint);
    }

    @Override
    public LocationPoint updateLocationPoint(@Valid LocationPoint locationPoint) {
        return locationPointRepository.save(locationPoint);
    }

    @Override
    public void deleteLocationPointById(Long id) {
        locationPointRepository.deleteById(id);
    }
}
