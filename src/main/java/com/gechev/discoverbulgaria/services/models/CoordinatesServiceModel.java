package com.gechev.discoverbulgaria.services.models;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class CoordinatesServiceModel {

    @Expose
    @NotNull(message = "Longitude cannot be empty.")
    private Double longitude;

    @Expose
    @NotNull(message = "Latitude cannot be empty.")
    private Double latitude;
}
