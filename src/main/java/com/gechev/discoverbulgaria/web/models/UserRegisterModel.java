package com.gechev.discoverbulgaria.web.models;

import com.gechev.discoverbulgaria.validations.annotations.PasswordMatches;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@PasswordMatches
public class UserRegisterModel {

    @NotEmpty(message = "Липсва потребителско име.")
    private String username;

    @NotNull(message = "Липсва парола.")
    @Pattern(regexp = "(?=^.{6,}$)(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s)[0-9a-zA-Z!@#$%^&*()]*$", message = "Паролата трябва да съдържа най-малко 6 символа, поне една главна буква и една цифра.")
    private String password;

    private String confirmPassword;

    @NotNull(message = "Имейлът е задължителен.")
    @Pattern(regexp = "[a-zA-Z0-9._]+@[a-zA-Z]+\\.[a-z]{2,4}", message = "Невалиден имейл.")
    private String email;
}
