package com.gechev.discoverbulgaria.services.models;

import com.gechev.discoverbulgaria.data.models.Type;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PoiServiceModel {

    private String name;

    private String address;

    private Type type;

    private CoordinatesServiceModel coordinates;

    private RegionServiceModel region;
}
