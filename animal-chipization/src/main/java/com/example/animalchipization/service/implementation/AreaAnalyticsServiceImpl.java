package com.example.animalchipization.service.implementation;

import com.example.animalchipization.data.projection.IdOnly;
import com.example.animalchipization.data.repository.AreaAnalyticsRepository;
import com.example.animalchipization.entity.AreaAnalytics;
import com.example.animalchipization.service.AreaAnalyticsService;
import com.example.animalchipization.web.model.AreaAnalyticsPresentationModel;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.example.animalchipization.data.specification.AreaAnalyticsSpecificationFactory.*;

@Service
public class AreaAnalyticsServiceImpl implements AreaAnalyticsService {

    private final AreaAnalyticsRepository analyticsRepository;
    private final Converter<Collection<AreaAnalytics>, AreaAnalyticsPresentationModel> analyticsConverter;

    @Autowired
    public AreaAnalyticsServiceImpl(
            AreaAnalyticsRepository analyticsRepository,
            Converter<Collection<AreaAnalytics>, AreaAnalyticsPresentationModel> analyticsConverter) {

        this.analyticsRepository = analyticsRepository;
        this.analyticsConverter = analyticsConverter;
    }

    @Override
    public AreaAnalyticsPresentationModel generateAreaAnalyticsPresentation(@Min(1) Long areaId,
                                                                            @NotNull LocalDate startDate,
                                                                            @NotNull LocalDate endDate) {

        if (!analyticsRepository.existsById(areaId)) {
            throw new NoSuchElementException();
        }

        /*
        Specification<AreaAnalytics> specifications = Specification.where(
                hasAreaId(areaId)
                        .and(hasDateGreaterThanOrEqualTo(startDate))
                        .and(hasDateLessThanOrEqualTo(endDate))
        );

        Collection<Long> ids = analyticsRepository.findAllId(specifications).stream()
                .map(IdOnly::getId)
                .collect(Collectors.toList());
            */
        Collection<Long> ids = analyticsRepository
                .findAllByArea_IdAndDateBetween(areaId, startDate, endDate).stream()
                .map(IdOnly::getId)
                .collect(Collectors.toList());
        Collection<AreaAnalytics> distinctAnalytics = analyticsRepository.
                findDistinctOnAnimalAndStatusOfVisitAndIdIn(ids);

        return analyticsConverter.convert(distinctAnalytics);
    }

}
