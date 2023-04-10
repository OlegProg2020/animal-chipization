package com.example.animalchipization.service.implementation;

import com.example.animalchipization.data.repository.LocationPointRepository;
import com.example.animalchipization.entity.LocationPoint;
import com.example.animalchipization.exception.LocationPointWithSuchCoordinatesAlreadyExistsException;
import com.example.animalchipization.service.LocationPointService;
import com.example.animalchipization.service.mapper.Mapper;
import com.example.animalchipization.web.dto.LocationPointDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class LocationPointServiceImpl implements LocationPointService {

    private final LocationPointRepository locationPointRepository;
    private final Mapper<LocationPoint, LocationPointDto> mapper;

    @Autowired
    public LocationPointServiceImpl(LocationPointRepository locationPointRepository,
                                    Mapper<LocationPoint, LocationPointDto> mapper) {
        this.locationPointRepository = locationPointRepository;
        this.mapper = mapper;
    }

    @Override
    public LocationPointDto findLocationPointById(@Min(1) Long pointId) {
        return mapper.toDto(locationPointRepository.findById(pointId)
                .orElseThrow(NoSuchElementException::new));
    }

    @Override
    @Transactional
    public LocationPointDto addLocationPoint(@Valid LocationPointDto locationPointDto) {
        try {
            return mapper.toDto(locationPointRepository.save(mapper.toEntity(locationPointDto)));
        } catch (DataIntegrityViolationException exception) {
            throw new LocationPointWithSuchCoordinatesAlreadyExistsException();
        }
    }

    @Override
    @Transactional
    public LocationPointDto updateLocationPoint(@Valid LocationPointDto locationPointDto) {
        LocationPoint updatingLocationPoint = mapper.toEntity(locationPointDto);
        if (locationPointRepository.existsById(updatingLocationPoint.getId())) {
            try {
                return mapper.toDto(locationPointRepository.save(updatingLocationPoint));
            } catch (DataIntegrityViolationException exception) {
                throw new LocationPointWithSuchCoordinatesAlreadyExistsException();
            }
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    @Transactional
    public void deleteLocationPointById(@Min(1) Long id) {
        try {
            locationPointRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ignoredException) {
            throw new NoSuchElementException();
        }
    }

}
