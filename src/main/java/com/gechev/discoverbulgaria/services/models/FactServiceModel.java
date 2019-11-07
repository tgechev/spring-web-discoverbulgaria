package com.gechev.discoverbulgaria.services.models;

import com.gechev.discoverbulgaria.data.models.Type;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FactServiceModel {

    private String description;

    private Type type;

    private RegionServiceModel region;
}
