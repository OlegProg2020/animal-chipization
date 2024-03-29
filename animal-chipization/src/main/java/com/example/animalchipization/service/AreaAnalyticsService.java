package com.example.animalchipization.service;

import com.example.animalchipization.entity.AreaAnalytics;
import com.example.animalchipization.web.model.AreaAnalyticsPresentationModel;

import java.time.LocalDate;

public interface AreaAnalyticsService {

    AreaAnalyticsPresentationModel generateAreaAnalyticsPresentation(Long areaId,
                                                                     LocalDate startDate,
                                                                     LocalDate endDate);

    void save(AreaAnalytics areaAnalytics);

}
