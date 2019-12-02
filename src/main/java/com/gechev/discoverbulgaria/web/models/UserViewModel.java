package com.gechev.discoverbulgaria.web.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserViewModel {
    private String username;
    private boolean isAdmin;
}
