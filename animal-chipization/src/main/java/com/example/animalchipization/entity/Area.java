package com.example.animalchipization.entity;

import com.example.animalchipization.data.repository.converter.JpaPolygonToJtsPolygonAttributeConverter;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Polygon;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class Area {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    @Column(columnDefinition = "polygon")
    @Convert(converter = JpaPolygonToJtsPolygonAttributeConverter.class)
    private Polygon areaPoints;

}
