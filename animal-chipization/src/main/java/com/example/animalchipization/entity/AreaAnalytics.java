package com.example.animalchipization.entity;

import com.example.animalchipization.entity.enums.StatusOfAnimalVisitToArea;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"area", "animal", "dateTime", "statusOfVisit"})})
public class AreaAnalytics {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(optional = false, cascade = CascadeType.REMOVE)
    private Area area;
    @ManyToOne(optional = false, cascade = CascadeType.REMOVE)
    private Animal animal;
    private LocalDate dateTime;
    @Enumerated(EnumType.STRING)
    private StatusOfAnimalVisitToArea statusOfVisit;

}
