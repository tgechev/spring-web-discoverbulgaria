package com.gechev.discoverbulgaria.services;

import com.gechev.discoverbulgaria.base.TestBase;
import com.gechev.discoverbulgaria.data.models.Role;
import com.gechev.discoverbulgaria.data.repositories.RoleRepository;
import com.gechev.discoverbulgaria.services.models.RoleServiceModel;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoleServiceTest extends TestBase {
    @MockBean
    ValidationService validationService;

    @MockBean
    RoleRepository roleRepository;

    @Autowired
    RoleService roleService;

    @Test
    public void findAllRoles_whenThereAreRoles_shouldReturnThemMapped(){
        Role user = new Role("user");
        Role admin = new Role("admin");
        Role root = new Role("root");
        List<Role> roles = new ArrayList<>();
        roles.add(user);
        roles.add(admin);
        roles.add(root);

        Mockito.when(roleRepository.findAll()).thenReturn(roles);

        Set<RoleServiceModel> actualRoles = roleService.findAllRoles();

        assertEquals(roles.size(), actualRoles.size());
        assertEquals(RoleServiceModel.class, actualRoles.toArray()[0].getClass());
    }



}
