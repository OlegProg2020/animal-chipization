package com.example.animalchipization.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class AnimalVisitedLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private ZonedDateTime dateTimeOfVisitLocationPoint = ZonedDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    @ManyToOne(optional = false)
    private LocationPoint locationPoint;
    @ManyToOne(optional = false)
    private Animal animal;

}
