package com.example.animalchipization.service.implementation;

import com.example.animalchipization.data.repository.AreaAnalyticsRepository;
import com.example.animalchipization.data.repository.AreaRepository;
import com.example.animalchipization.entity.AreaAnalytics;
import com.example.animalchipization.service.AreaAnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.NoSuchElementException;

import static com.example.animalchipization.data.specification.AreaAnalyticsSpecificationFactory.*;

@Service
public class AreaAnalyticsServiceImpl implements AreaAnalyticsService {

    private final AreaAnalyticsRepository analyticsRepository;
    private final AreaRepository areaRepository;

    @Autowired
    public AreaAnalyticsServiceImpl(AreaAnalyticsRepository analyticsRepository,
                                    AreaRepository areaRepository) {

        this.analyticsRepository = analyticsRepository;
        this.areaRepository = areaRepository;
    }

    @Override
    public Collection<AreaAnalytics> searchForAreaAnalytics(Long areaId, LocalDate startDate, LocalDate endDate) {
        if (!analyticsRepository.existsById(areaId)) {
            throw new NoSuchElementException();
        }

        Specification<AreaAnalytics> specifications = Specification.where(
                hasAreaId(areaId)
                        .and(hasDateGreaterThanOrEqualTo(startDate))
                        .and(hasDateLessThanOrEqualTo(endDate))
        );

        return analyticsRepository.findAll(specifications);

    }

}
