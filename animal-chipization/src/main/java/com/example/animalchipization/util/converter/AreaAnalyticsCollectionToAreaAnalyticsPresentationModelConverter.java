package com.example.animalchipization.util.converter;

import com.example.animalchipization.entity.AnimalType;
import com.example.animalchipization.entity.AreaAnalytics;
import com.example.animalchipization.entity.enums.StatusOfAnimalVisitToArea;
import com.example.animalchipization.web.model.AnimalAnalytics;
import com.example.animalchipization.web.model.AreaAnalyticsPresentationModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AreaAnalyticsCollectionToAreaAnalyticsPresentationModelConverter
        implements Converter<Collection<AreaAnalytics>, AreaAnalyticsPresentationModel> {

    @Override
    public AreaAnalyticsPresentationModel convert(Collection<AreaAnalytics> source) {
        AreaAnalyticsPresentationModel presentation = new AreaAnalyticsPresentationModel();

        presentation.setTotalAnimalsArrived(getAnimalsArrived(source));
        presentation.setTotalAnimalsGone(getAnimalsGone(source));
        long arrivedGoneDifference = presentation.getTotalAnimalsArrived() - presentation.getTotalAnimalsGone();
        presentation.setTotalQuantityAnimals(arrivedGoneDifference >= 0L ? arrivedGoneDifference : 0L);
        presentation.setAnimalsAnalytics(getAnimalsAnalytics(source));
        return presentation;
    }

    private Long getAnimalsArrived(Collection<AreaAnalytics> analyticsCollection) {
        return analyticsCollection.stream()
                .filter(a -> a.getStatusOfVisit() == StatusOfAnimalVisitToArea.ARRIVED)
                .count();
    }

    private Long getAnimalsGone(Collection<AreaAnalytics> analyticsCollection) {
        return analyticsCollection.stream()
                .filter(a -> a.getStatusOfVisit() == StatusOfAnimalVisitToArea.GONE)
                .count();
    }

    private Collection<AnimalAnalytics> getAnimalsAnalytics(
            Collection<AreaAnalytics> analyticsCollection) {

        Map<AnimalType, AnimalsQuantity> typesAnalyticsMap = new HashMap<>();

        for (AreaAnalytics analytics : analyticsCollection) {
            Set<AnimalType> animalTypes = analytics.getAnimal().getAnimalTypes();

            for (AnimalType type : animalTypes) {
                typesAnalyticsMap.putIfAbsent(type, new AnimalsQuantity());
                typesAnalyticsMap.computeIfPresent(type, (key, value) -> {
                    switch (analytics.getStatusOfVisit()) {
                        case ARRIVED -> value.arrived++;
                        case GONE -> value.gone++;
                    }
                    return value;
                });
            }
        }

        return convertTypesAnalyticsMapToAnimalsAnalytics(typesAnalyticsMap);
    }

    private static class AnimalsQuantity {
        long arrived = 0;
        long gone = 0;
    }

    private Collection<AnimalAnalytics> convertTypesAnalyticsMapToAnimalsAnalytics(
            Map<AnimalType, AnimalsQuantity> typesAnalyticsMap) {

        return typesAnalyticsMap.entrySet().stream()
                .map(entry -> {
                    AnimalAnalytics analytics = new AnimalAnalytics();
                    AnimalType type = entry.getKey();
                    analytics.setAnimalType(type.getType());
                    analytics.setAnimalTypeId(type.getId());
                    AnimalsQuantity quantity = entry.getValue();
                    analytics.setAnimalsArrived(quantity.arrived);
                    analytics.setAnimalsGone(quantity.gone);
                    long arrivedGoneDifference = quantity.arrived - quantity.gone;
                    analytics.setQuantityAnimals(arrivedGoneDifference >= 0L ? arrivedGoneDifference : 0L);
                    return analytics;
                })
                .collect(Collectors.toList());
    }

}
