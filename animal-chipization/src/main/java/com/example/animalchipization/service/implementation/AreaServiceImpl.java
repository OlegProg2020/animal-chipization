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
        Polygon polygon = area.getAreaPoints();
        if(!polygon.isValid()) {
            throw new InvalidAreaPointsPolygonException();
        }
        if(areaRepository.existsByAreaPoints(polygon)) {
            throw new AreaWithSuchAreaPointsAlreadyExistsException();
        }
        List<Polygon> overlaps = areaRepository.findAreaOverlapsByAreaPoints(polygon).stream()
                .map(pgPolygonToJtsPolygonConverter::convert)
                .collect(Collectors.toList());
        // touches() возвращает true, если границы двух полигонов
        // касаются, но не пересекаются.
        // В данном случае из бд вернутся только те Polygon, которые имеют
        // как точки соприкосновения, так и точки пересечения.
        // Кроме того будут получены зоны, содержат данную зону или
        // которые содержатся внутри данной зоны.
        // Для проверки этого случая используется метод...
        // http://locationtech.github.io/jts/javadoc/org/locationtech/jts/geom/Geometry.html#covers-org.locationtech.jts.geom.Geometry-
        // может все заменить на disjoint() ???
        for(var overlap : overlaps) {
            if(!overlap.touches(polygon)) {
                throw new AreaIntersectsWithExistingAreaException();
            }
        }
    }

    @Override
    @Transactional
    public AreaDto update(AreaDto areaDto) {
        return null;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {

    }

}
