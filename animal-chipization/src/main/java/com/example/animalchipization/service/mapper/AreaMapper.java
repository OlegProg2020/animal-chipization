package com.example.animalchipization.service.mapper;

import com.example.animalchipization.dto.AreaDto;
import com.example.animalchipization.entity.Area;
import com.example.animalchipization.web.model.Coordinate;
import jakarta.annotation.PostConstruct;
import org.locationtech.jts.geom.Polygon;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AreaMapper extends DefaultMapper<Area, AreaDto> {

    private final ModelMapper mapper;
    private final Converter<Polygon, List<Coordinate>> polygonToCoordinatesConverter;
    private final Converter<List<Coordinate>, Polygon> coordinatesToPolygonConverter;

    @Autowired
    public AreaMapper(ModelMapper mapper,
                      Converter<Polygon, List<Coordinate>> polygonToCoordinatesConverter,
                      Converter<List<Coordinate>, Polygon> coordinatesToPolygonConverter) {

        super(mapper, Area.class, AreaDto.class);
        this.mapper = mapper;
        this.polygonToCoordinatesConverter = polygonToCoordinatesConverter;
        this.coordinatesToPolygonConverter = coordinatesToPolygonConverter;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Area.class, AreaDto.class)
                .setPropertyConverter(polygonToCoordinatesConverter);
        mapper.createTypeMap(AreaDto.class, Area.class)
                .setPropertyConverter(coordinatesToPolygonConverter);
    }

}
