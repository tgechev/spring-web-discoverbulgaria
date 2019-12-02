package com.gechev.discoverbulgaria.services;

import com.gechev.discoverbulgaria.services.models.RoleServiceModel;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RoleService {
    List<RoleServiceModel> findAllRoles();
    RoleServiceModel findByAuthority(String role);
    void seedRoles(RoleServiceModel[] roleServiceModels);
}
