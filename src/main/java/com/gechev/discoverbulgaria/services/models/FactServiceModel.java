package com.gechev.discoverbulgaria.services.models;

import com.gechev.discoverbulgaria.data.models.Type;
import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FactServiceModel extends BaseServiceModel {

    @Expose
    @Size(min = 3, max = 30, message = "Fact title should be between 3 and 30 symbols.")
    private String title;

    @Expose
    @Size(min = 100, max = 500, message = "Fact description should be between 100 and 500 symbols:")
    private String description;

    @Expose
    @NotNull(message = "Fact type should be either HISTORY or NATURE.")
    private Type type;

    @Expose
    private RegionServiceModel region;

    @Expose
    @NotNull(message = "Image url is required.")
    private String imageUrl;

    @Expose
    private String readMore;

    @Expose
    private String videoId;
}
