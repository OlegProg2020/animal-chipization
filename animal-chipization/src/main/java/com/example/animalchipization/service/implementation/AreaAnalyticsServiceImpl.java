package com.example.animalchipization.service.implementation;

import com.example.animalchipization.data.projection.IdOnly;
import com.example.animalchipization.data.repository.AreaAnalyticsRepository;
import com.example.animalchipization.data.repository.AreaRepository;
import com.example.animalchipization.entity.AreaAnalytics;
import com.example.animalchipization.service.AreaAnalyticsService;
import com.example.animalchipization.web.model.AreaAnalyticsPresentationModel;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class AreaAnalyticsServiceImpl implements AreaAnalyticsService {

    private final AreaAnalyticsRepository analyticsRepository;
    private final Converter<Collection<AreaAnalytics>, AreaAnalyticsPresentationModel> analyticsConverter;
    private final AreaRepository areaRepository;

    @Autowired
    public AreaAnalyticsServiceImpl(
            AreaAnalyticsRepository analyticsRepository,
            Converter<Collection<AreaAnalytics>, AreaAnalyticsPresentationModel> analyticsConverter,
            AreaRepository areaRepository) {

        this.analyticsRepository = analyticsRepository;
        this.analyticsConverter = analyticsConverter;
        this.areaRepository = areaRepository;
    }

    @Override
    public AreaAnalyticsPresentationModel generateAreaAnalyticsPresentation(@Min(1) Long areaId,
                                                                            @NotNull LocalDate startDate,
                                                                            @NotNull LocalDate endDate) {

        if (!areaRepository.existsById(areaId)) {
            throw new NoSuchElementException();
        }

        Collection<Long> ids = analyticsRepository
                .findAllByArea_IdAndDateBetween(areaId, startDate, endDate).stream()
                .map(IdOnly::getId)
                .collect(Collectors.toList());
        ids = analyticsRepository.
                findDistinctOnAnimalAndStatusOfVisitAndIdIn(ids).stream()
                .map(IdOnly::getId)
                .collect(Collectors.toList());
        Collection<AreaAnalytics> analyticsCollection = analyticsRepository.findAllByIdIn(ids);

        return analyticsConverter.convert(analyticsCollection);
    }

    @Override
    public void save(AreaAnalytics areaAnalytics) {
        analyticsRepository.save(areaAnalytics);
    }

}
