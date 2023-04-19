package com.example.animalchipization.service.validation.implementation;

import com.example.animalchipization.data.repository.AreaRepository;
import com.example.animalchipization.entity.Area;
import com.example.animalchipization.exception.AreaIntersectsWithExistingAreaException;
import com.example.animalchipization.exception.InvalidAreaPointsPolygonException;
import com.example.animalchipization.service.validation.AreaBusinessRulesValidator;
import org.locationtech.jts.geom.Polygon;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AreaBusinessRulesValidatorImpl implements AreaBusinessRulesValidator {

    private final AreaRepository areaRepository;

    public AreaBusinessRulesValidatorImpl(AreaRepository areaRepository) {
        this.areaRepository = areaRepository;
    }

    @Override
    public void validateArea(Area area) {
        Polygon polygon = area.getAreaPoints();

        if (!polygon.isValid()) {
            throw new InvalidAreaPointsPolygonException();
        }

        Collection<Area> areaOverlaps = areaRepository.findAreaOverlapsByAreaPoints(polygon);
        if (area.getId() != null) {
            areaOverlaps.removeIf(a -> area.getId().equals(a.getId()));
        }
        List<Polygon> overlaps = areaOverlaps.stream()
                .map(Area::getAreaPoints)
                .collect(Collectors.toList());
        for (var overlap : overlaps) {
            if (!overlap.touches(polygon)) {
                throw new AreaIntersectsWithExistingAreaException();
            }
        }
    }

}
