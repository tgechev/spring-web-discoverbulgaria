package com.gechev.discoverbulgaria.services;

import com.gechev.discoverbulgaria.services.models.RoleServiceModel;

import java.util.List;

public interface RoleService {
    List<RoleServiceModel> findAllRoles();
    RoleServiceModel findByAuthority(String role);
    void seedRoles(RoleServiceModel[] roleServiceModels);
    Long getRepositoryCount();
}
