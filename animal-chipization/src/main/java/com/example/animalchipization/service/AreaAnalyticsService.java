package com.example.animalchipization.service;

import com.example.animalchipization.entity.AreaAnalytics;

import java.time.LocalDate;
import java.util.Collection;

public interface AreaAnalyticsService {

    Collection<AreaAnalytics> searchForAreaAnalytics(Long areaId, LocalDate startDate, LocalDate endDate);

}
