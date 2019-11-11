package com.gechev.discoverbulgaria.services;

import com.gechev.discoverbulgaria.services.models.RoleServiceModel;

import java.util.Optional;
import java.util.Set;

public interface RoleService {
    Set<RoleServiceModel> findAllRoles();
    RoleServiceModel findByAuthority(String role);
    void seedRoles(RoleServiceModel[] roleServiceModels);
}
