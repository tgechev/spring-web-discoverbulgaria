package com.gechev.discoverbulgaria.web.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegionViewModel {
    private String id;
    private String name;
    private String regionId;
    private Double area;
    private Integer population;
    private String imageUrl;
}
