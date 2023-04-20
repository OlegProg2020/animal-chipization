package com.example.animalchipization.web.controller;

import com.example.animalchipization.exception.StartDateGreaterThanOrEqualToEndDateException;
import com.example.animalchipization.service.AreaAnalyticsService;
import com.example.animalchipization.web.model.AreaAnalyticsPresentationModel;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping(path = "/areas", produces = "application/json")
@Validated
public class AreaAnalyticsController {

    private final AreaAnalyticsService analyticsService;

    @Autowired
    public AreaAnalyticsController(AreaAnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/{areaId}/analytics")
    public ResponseEntity<AreaAnalyticsPresentationModel> getAnalytics(
            @PathVariable("areaId") @Min(1) Long areaId,
            @RequestParam("startDate") @NotNull LocalDate startDate,
            @RequestParam("endDate") @NotNull LocalDate endDate) {

        if (startDate.isAfter(endDate) || startDate.isEqual(endDate)) {
            throw new StartDateGreaterThanOrEqualToEndDateException();
        }

        return new ResponseEntity<>(
                analyticsService.generateAreaAnalyticsPresentation(areaId, startDate, endDate),
                HttpStatus.valueOf(200));
    }

}
