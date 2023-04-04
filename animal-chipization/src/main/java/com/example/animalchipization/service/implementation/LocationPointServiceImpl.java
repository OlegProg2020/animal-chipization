package com.example.animalchipization.service.implementation;

import com.example.animalchipization.data.repository.LocationPointRepository;
import com.example.animalchipization.exception.LocationPointWithSuchCoordinatesAlreadyExistsException;
import com.example.animalchipization.model.LocationPoint;
import com.example.animalchipization.service.LocationPointService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
        if (!locationPointRepository.existsByLatitudeAndLongitudeIs(locationPoint.getLatitude(),
                locationPoint.getLongitude())) {
            return locationPointRepository.save(locationPoint);
        } else {
            throw new LocationPointWithSuchCoordinatesAlreadyExistsException();
        }
    }

    @Override
    public LocationPoint updateLocationPoint(@Valid LocationPoint locationPoint) {
        if (locationPointRepository.existsById(locationPoint.getId())) {
            if (!locationPointRepository.existsByLatitudeAndLongitudeIs(locationPoint.getLatitude(),
                    locationPoint.getLongitude())) {
                return locationPointRepository.save(locationPoint);
            } else {
                throw new LocationPointWithSuchCoordinatesAlreadyExistsException();
            }
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public void deleteLocationPointById(Long id) {
        try {
            locationPointRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ignoredException) {
            throw new NoSuchElementException();
        }
    }

}
