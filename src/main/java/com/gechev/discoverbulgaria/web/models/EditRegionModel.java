package com.gechev.discoverbulgaria.web.models;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
public class EditRegionModel {

    @NotEmpty(message = "Моля въведете име.")
    private String name;

    @Pattern(regexp ="\\b((BG-0[1-9])|(BG-1[0-9])|(BG-2[0-8]))\\b", message = "ID номерът трябва да е попълнен във формат 'BG-{XX}', където XX е число от 01 до 28.")
    private String regionId;

    @NotNull(message = "Моля въведете площ.")
    private Double area;

    @NotNull(message = "Моля въведете население.")
    private Integer population;

    private String theId;
}