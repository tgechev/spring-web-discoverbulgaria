package com.gechev.discoverbulgaria.services.models;

import com.gechev.discoverbulgaria.data.models.Type;
import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class PoiServiceModel {

    @Expose
    @NotNull(message = "POI title cannot be empty.")
    private String title;

    @Expose
    private String address;

    @Expose
    @NotNull(message = "POI description cannot be empty.")
    @Size(min = 100, max = 1000, message = "POI description should be between 100 and 1000 symbols:")
    private String description;

    @Expose
    @NotNull(message = "POI type should be either HISTORY or NATURE.")
    private Type type;

    @Expose
    @Valid
    private CoordinatesServiceModel coordinates;

    @Expose
    @NotNull(message = "POI region cannot be empty.")
    private RegionServiceModel region;

    @Expose
    @NotNull(message = "Image url is required.")
    private String imageUrl;

    @Expose
    private String readMore;
}
