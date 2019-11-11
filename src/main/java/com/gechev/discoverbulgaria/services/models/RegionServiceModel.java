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
    @NotNull(message = "Region ID cannot be empty.")
    @Pattern(regexp = "BG-[0-9]{2}", message = "Region id should be of the form 'BG_{nn}' where nn is a number from 01 to 28:")
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
    @Expose
    private Set<PoiServiceModel> poi;
    @Expose
    private Set<FactServiceModel> facts;
}
