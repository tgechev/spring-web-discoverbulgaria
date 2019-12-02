package com.gechev.discoverbulgaria.services.models;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
public class RoleServiceModel extends BaseServiceModel {

    @Expose
    @NotNull(message = "Role cannot be empty.")
    @Pattern(regexp = "ROLE_+[A-Z]{4,8}", message = "Roles should have the following syntax: ROLE_{the role}, where {the role} should be between 4 and 8 symbols:")
    private String authority;

    @Override
    public boolean equals(Object obj) {

        if(obj.getClass() != RoleServiceModel.class){
            return false;
        }

        RoleServiceModel otherRole = (RoleServiceModel) obj;
        return this.getId().equals(otherRole.getId()) && this.authority.equals(otherRole.getAuthority());
    }
}
