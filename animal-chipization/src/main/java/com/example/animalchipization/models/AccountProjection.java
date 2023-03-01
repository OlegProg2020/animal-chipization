package com.example.animalchipization.models;

import org.springframework.data.rest.core.config.Projection;

@Projection(types = {Account.class})
public interface AccountProjection {

    Long getId();
    String getFirstName();
    String getLastName();
    String getEmail();

}
