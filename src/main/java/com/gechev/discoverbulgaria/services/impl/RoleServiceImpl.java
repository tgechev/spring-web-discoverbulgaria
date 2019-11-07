package com.gechev.discoverbulgaria.services.impl;

import com.gechev.discoverbulgaria.services.RoleService;
import com.gechev.discoverbulgaria.services.models.RoleServiceModel;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {
    @Override
    public Set<RoleServiceModel> findAllRoles() {
        return null;
    }

    @Override
    public RoleServiceModel findByAuthority(String role) {
        return null;
    }
}
