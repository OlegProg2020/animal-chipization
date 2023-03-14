package com.example.animalchipization.web.form;

import com.example.animalchipization.model.enums.Gender;
import com.example.animalchipization.model.enums.LifeStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Component
public class AnimalForm {
    @NotEmpty
    private List<@NotNull @Min(1) Long> animalTypes = new ArrayList<>();
    @NotNull
    @DecimalMin(value = "0", inclusive = false)
    private Float weight;
    @NotNull
    @DecimalMin(value = "0", inclusive = false)
    private Float length;
    @NotNull
    @DecimalMin(value = "0", inclusive = false)
    private Float height;
    @NotNull
    private Gender gender;
    private LifeStatus lifeStatus;
    @NotNull
    @Min(1)
    private Long chipperId;
    @NotNull
    @Min(1)
    private Long chippingLocationId;

}
