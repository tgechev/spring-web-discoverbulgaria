package com.gechev.discoverbulgaria.web.models;

import com.gechev.discoverbulgaria.data.models.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FactFormViewModel {
    @Size(min = 3, max = 100, message = "Заглавието трябва да бъде между 3 и 100 символа.")
    private String title;

    @NotEmpty(message = "Моля изберете факт.")
    private String oldTitle;

    @NotNull(message = "Моля изберете тип.")
    private Type type;

    @NotEmpty(message = "Моля изберете област.")
    private String regionId;

    @Size(min = 100, max = 500, message = "Описанието трябва да бъде между 100 и 500 символа.")
    private String description;

    @NotEmpty(message = "Моля прикачете снимка.")
    private String imageUrl;

    @Pattern(regexp = "(^$|^(https?|ftp)://[^\\s/$.?#].[^\\s]*$)", message = "Невалидна интернет връзка")
    private String readMore;
}
