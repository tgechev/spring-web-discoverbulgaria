package com.gechev.discoverbulgaria.services.models;

import com.gechev.discoverbulgaria.data.models.Type;
import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class FactServiceModel {

    @Expose
    @Size(min = 100, max = 500, message = "Fact description should be between 100 and 500 symbols:")
    private String description;

    @Expose
    @NotNull(message = "Fact type should be either HISTORY or NATURE.")
    private Type type;

    @Expose
    private RegionServiceModel region;
}
