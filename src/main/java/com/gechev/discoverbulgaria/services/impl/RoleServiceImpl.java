package com.gechev.discoverbulgaria.services.impl;

import com.gechev.discoverbulgaria.data.models.Role;
import com.gechev.discoverbulgaria.data.repositories.RoleRepository;
import com.gechev.discoverbulgaria.services.RoleService;
import com.gechev.discoverbulgaria.services.ValidationService;
import com.gechev.discoverbulgaria.services.models.RoleServiceModel;
import com.gechev.discoverbulgaria.util.ValidatorUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final ValidationService validationService;
    private final RoleRepository roleRepository;
    private final ModelMapper mapper;

    public RoleServiceImpl(ValidationService validationService, RoleRepository roleRepository, ModelMapper mapper) {
        this.validationService = validationService;
        this.roleRepository = roleRepository;
        this.mapper = mapper;
    }

    @Override
    public List<RoleServiceModel> findAllRoles() {
        return this.roleRepository.findAll()
                .stream()
                .map(r -> this.mapper.map(r, RoleServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public RoleServiceModel findByAuthority(String role) {
        return this.mapper.map(this.roleRepository.findByAuthority(role).get(), RoleServiceModel.class);
    }

    @Override
    public void seedRoles(RoleServiceModel[] roleServiceModels) {

        for (RoleServiceModel roleServiceModel : roleServiceModels) {

            //Validate role model and print message if not valid
            if(!this.validationService.isValid(roleServiceModel)){
                this.validationService.violations(roleServiceModel)
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
                Role role = this.mapper.map(roleServiceModel, Role.class);
                this.roleRepository.saveAndFlush(role);
                System.out.println(String.format("Role %s successfully created.", role.getAuthority()));
            }
        }
    }
}
