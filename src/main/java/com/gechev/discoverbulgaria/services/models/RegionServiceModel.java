package com.gechev.discoverbulgaria.services.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RegionServiceModel {

    private String name;

    private Integer population;

    private Double area;

    private Set<PoiServiceModel> poi;

    private Set<FactServiceModel> facts;
}
