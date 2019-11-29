package com.gechev.discoverbulgaria.services.models;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RegionServiceModel {

    @Expose
    @Pattern(regexp = "\\b((BG-0[1-9])|(BG-1[0-9])|(BG-2[0-8]))\\b", message = "Region id should be of the form 'BG-{XX}' where XX is a number from 01 to 28:")
    private String regionId;
    @Expose
    @NotNull(message = "Region name cannot be empty.")
    private String name;
    @Expose
    @NotNull(message = "Population cannot be empty.")
    private Integer population;
    @Expose
    @NotNull(message = "Area cannot be empty.")
    private Double area;

    private String imageUrl;

    @Expose
    private Set<PoiServiceModel> poi;
    @Expose
    private Set<FactServiceModel> facts;
}
