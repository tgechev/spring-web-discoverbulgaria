package com.gechev.discoverbulgaria.services.models;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserServiceModel extends BaseServiceModel {

    @Expose
    @NotNull(message = "Username cannot be empty.")
    private String username;

    @Expose
    @NotNull(message = "Password cannot be empty.")
    @Pattern(regexp = "(?=^.{6,}$)(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s)[0-9a-zA-Z!@#$%^&*()]*$", message = "Invalid password.")
    private String password;

    @Expose
    @NotNull(message = "Email cannot be empty.")
    @Pattern(regexp = "[a-zA-Z0-9._]+@[a-zA-Z]+\\.[a-z]{2,4}", message = "Invalid email.")
    private String email;

    @Expose
    @NotNull(message = "User should have at least one role.")
    private List<RoleServiceModel> authorities;

}