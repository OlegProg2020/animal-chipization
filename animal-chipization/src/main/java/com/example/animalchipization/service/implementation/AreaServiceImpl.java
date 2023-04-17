package com.example.animalchipization.service.implementation;

import com.example.animalchipization.data.repository.AreaRepository;
import com.example.animalchipization.dto.AreaDto;
import com.example.animalchipization.entity.Area;
import com.example.animalchipization.exception.AreaIntersectsWithExistingAreaException;
import com.example.animalchipization.exception.AreaWithSuchAreaPointsAlreadyExistsException;
import com.example.animalchipization.exception.InvalidAreaPointsPolygonException;
import com.example.animalchipization.service.AreaService;
import com.example.animalchipization.service.mapper.Mapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.locationtech.jts.geom.Polygon;
import org.postgresql.geometric.PGpolygon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class AreaServiceImpl implements AreaService {

    private final AreaRepository areaRepository;
    private final Mapper<Area, AreaDto> mapper;
    private final Converter<PGpolygon, Polygon> pgPolygonToJtsPolygonConverter;

    @Autowired
    public AreaServiceImpl(AreaRepository areaRepository,
                           Mapper<Area, AreaDto> mapper,
                           Converter<PGpolygon, Polygon> pgPolygonToJtsPolygonConverter) {
        this.areaRepository = areaRepository;
        this.mapper = mapper;
        this.pgPolygonToJtsPolygonConverter = pgPolygonToJtsPolygonConverter;
    }

    @Override
    public AreaDto findById(@Min(1) Long id) {
        return mapper.toDto(areaRepository.findById(id).orElseThrow(NoSuchElementException::new));
    }

    @Override
    @Transactional
    public AreaDto save(@Valid AreaDto areaDto) {
        Area area = mapper.toEntity(areaDto);

        this.validateAreaPointsPolygon(area.getAreaPoints());

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

        this.validateAreaPointsPolygon(area.getAreaPoints());

        areaRepository.update(area);
        return areaDto;
    }

    @Override
    @Transactional
    public void deleteById(@Min(1) Long id) {
        try {
            areaRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ignoredException) {
            throw new NoSuchElementException();
        }
    }


    // helper methods

    private void validateAreaPointsPolygon(Polygon polygon) {
        if (!polygon.isValid()) {
            throw new InvalidAreaPointsPolygonException();
        }
        if (areaRepository.existsByAreaPoints(polygon)) {
            throw new AreaWithSuchAreaPointsAlreadyExistsException();
        }
        List<Polygon> overlaps = areaRepository.findAreaOverlapsByAreaPoints(polygon).stream()
                .map(pgPolygonToJtsPolygonConverter::convert)
                .collect(Collectors.toList());
        for (var overlap : overlaps) {
            if (!overlap.touches(polygon)) {
                throw new AreaIntersectsWithExistingAreaException();
            }
        }
    }

}
