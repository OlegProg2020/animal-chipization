package com.example.animalchipization.service.implementation;

import com.example.animalchipization.data.repository.AnimalRepository;
import com.example.animalchipization.data.repository.AnimalVisitedLocationRepository;
import com.example.animalchipization.model.AnimalVisitedLocation;
import com.example.animalchipization.service.AnimalVisitedLocationService;
import com.example.animalchipization.util.OffsetBasedPageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static com.example.animalchipization.data.specification.AnimalVisitedLocationSpecification.*;

@Service
public class AnimalVisitedLocationServiceImpl implements AnimalVisitedLocationService {

    private final AnimalRepository animalRepository;
    private final AnimalVisitedLocationRepository animalVisitedLocationRepository;

    @Autowired
    public AnimalVisitedLocationServiceImpl(AnimalRepository animalRepository,
                                            AnimalVisitedLocationRepository animalVisitedLocationRepository) {
        this.animalRepository = animalRepository;
        this.animalVisitedLocationRepository = animalVisitedLocationRepository;
    }

    @Override
    public Iterable<AnimalVisitedLocation> searchForAnimalVisitedLocations(Long animalId, LocalDateTime startDateTime,
                                                                    LocalDateTime endDateTime, Integer from,
                                                                    Integer size) {
        if (animalRepository.existsById(animalId)) {
            OffsetBasedPageRequest pageRequest = new OffsetBasedPageRequest(from, size,
                    Sort.by("dateTimeOfVisitLocationPoint").ascending());
            Specification<AnimalVisitedLocation> specifications = Specification.where(
                    hasAnimalId(animalId)
                            .and(hasDateTimeOfVisitLocationPointGreaterThanOrEqualTo(startDateTime))
                            .and(hasDateTimeOfVisitLocationPointLessThanOrEqualTo(endDateTime))
            );
            return animalVisitedLocationRepository.findAll(specifications, pageRequest).getContent();
        } else {
            throw new NoSuchElementException();
        }
    }
}
