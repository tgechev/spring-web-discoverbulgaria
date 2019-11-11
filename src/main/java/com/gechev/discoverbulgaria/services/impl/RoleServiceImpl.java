package com.gechev.discoverbulgaria.services.impl;

import com.gechev.discoverbulgaria.data.models.Role;
import com.gechev.discoverbulgaria.data.repositories.RoleRepository;
import com.gechev.discoverbulgaria.services.RoleService;
import com.gechev.discoverbulgaria.services.models.RoleServiceModel;
import com.gechev.discoverbulgaria.util.ValidatorUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final ValidatorUtil validatorUtil;
    private final RoleRepository roleRepository;
    private final ModelMapper mapper;

    public RoleServiceImpl(ValidatorUtil validatorUtil, RoleRepository roleRepository, ModelMapper mapper) {
        this.validatorUtil = validatorUtil;
        this.roleRepository = roleRepository;
        this.mapper = mapper;
    }

    @Override
    public Set<RoleServiceModel> findAllRoles() {
        return this.roleRepository.findAll()
                .stream()
                .map(r -> this.mapper.map(r, RoleServiceModel.class))
                .collect(Collectors.toSet());
    }

    @Override
    public RoleServiceModel findByAuthority(String role) {
        return this.mapper.map(this.roleRepository.findByAuthority(role), RoleServiceModel.class);
    }

    @Override
    public void seedRoles(RoleServiceModel[] roleServiceModels) {

        for (RoleServiceModel roleServiceModel : roleServiceModels) {

            //Validate role model and print message if not valid
            if(!validatorUtil.isValid(roleServiceModel)){
                this.validatorUtil.violations(roleServiceModel)
                        .forEach(v-> System.out.println(String.format("%s %s", v.getMessage(), v.getInvalidValue())));
                continue;
            }

            //If role already exists inform the user
            try{
                Role role = this.roleRepository.findByAuthority(roleServiceModel.getAuthority()).orElseThrow();
                System.out.println(String.format("Role %s already exists.", role.getAuthority()));
            }

            //If role does not exist, create it.
            catch (NoSuchElementException e){
                Role role = mapper.map(roleServiceModel, Role.class);
                this.roleRepository.saveAndFlush(role);
                System.out.println(String.format("Role %s successfully created.", role.getAuthority()));
            }
        }
    }
}
