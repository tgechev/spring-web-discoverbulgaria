package com.gechev.discoverbulgaria.web.models;

import com.gechev.discoverbulgaria.data.models.Type;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class AddFactModel {
    @Size(min = 3, max = 30, message = "Заглавието трябва да бъде между 3 и 30 символа.")
    private String title;
    private Type type;
    private String regionId;

    @Size(min = 100, max = 500, message = "Описанието на факта трябва да бъде между 100 и 500 символа.")
    private String description;

    private String imageUrl;

    @Pattern(regexp = "^(https?|ftp)://[^\\s/$.?#].[^\\s]*$", message = "Невалидна интернет връзка")
    private String readMore;
}
