package com.example.animalchipization.service.implementation;

import com.example.animalchipization.data.repository.AreaRepository;
import com.example.animalchipization.dto.AreaDto;
import com.example.animalchipization.entity.Area;
import com.example.animalchipization.exception.AreaIntersectsWithExistingAreaException;
import com.example.animalchipization.exception.InvalidAreaPointsPolygonException;
import com.example.animalchipization.service.AreaService;
import com.example.animalchipization.service.mapper.Mapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.locationtech.jts.geom.Polygon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class AreaServiceImpl implements AreaService {

    private final AreaRepository areaRepository;
    private final Mapper<Area, AreaDto> mapper;

    @Autowired
    public AreaServiceImpl(AreaRepository areaRepository,
                           Mapper<Area, AreaDto> mapper) {
        this.areaRepository = areaRepository;
        this.mapper = mapper;
    }

    @Override
    public AreaDto findById(@Min(1) Long id) {
        return mapper.toDto(areaRepository.findById(id).orElseThrow(NoSuchElementException::new));
    }

    @Override
    @Transactional
    public AreaDto save(@Valid AreaDto areaDto) {
        Area area = mapper.toEntity(areaDto);

        this.validateArea(area);

        area.setId(areaRepository.save(area));
        return mapper.toDto(area);
    }

    @Override
    @Transactional
    public AreaDto update(@Valid AreaDto areaDto) {
        Area area = mapper.toEntity(areaDto);
        if (!areaRepository.existsById(area.getId())) {
            throw new NoSuchElementException();
        }

        this.validateArea(area);

        areaRepository.update(area);
        return areaDto;
    }

    @Override
    @Transactional
    public void deleteById(@Min(1) Long id) {
        areaRepository.deleteById(id);
    }


    // helper methods

    private void validateArea(Area area) {
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
