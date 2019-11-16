package com.gechev.discoverbulgaria.services.impl;

import com.gechev.discoverbulgaria.data.models.Role;
import com.gechev.discoverbulgaria.data.models.User;
import com.gechev.discoverbulgaria.data.repositories.RoleRepository;
import com.gechev.discoverbulgaria.data.repositories.UserRepository;
import com.gechev.discoverbulgaria.services.UserService;
import com.gechev.discoverbulgaria.services.models.RoleServiceModel;
import com.gechev.discoverbulgaria.services.models.UserServiceModel;
import com.gechev.discoverbulgaria.util.ValidatorUtil;
import org.modelmapper.ModelMapper;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final ValidatorUtil validatorUtil;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;
    //private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(ValidatorUtil validatorUtil, RoleRepository roleRepository, UserRepository userRepository, ModelMapper mapper  /*,BCryptPasswordEncoder passwordEncoder*/) {
        this.validatorUtil = validatorUtil;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
        //this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserServiceModel registerUser(UserServiceModel userServiceModel) {
        return null;
    }

    @Override
    public UserServiceModel findByUsername(String username) {
        return null;
    }

    @Override
    public void seedUsers(UserServiceModel[] userServiceModels) {
        for (UserServiceModel userServiceModel : userServiceModels) {

            //Validate user model and print message if invalid
            if(!validatorUtil.isValid(userServiceModel)){
                this.validatorUtil.violations(userServiceModel)
                        .forEach(v-> System.out.println(String.format("%s %s", v.getMessage(), v.getInvalidValue())));
                continue;
            }

            //Check if user already exists.
            try{
                User user = this.userRepository.findByUsername(userServiceModel.getUsername()).orElseThrow();
                System.out.println(String.format("User %s already exists.", user.getUsername()));
            }

            //If user does not exist, create it.
            catch (NoSuchElementException e){

                //Check if the roles of the new user are all valid.
                try{
                    Set<RoleServiceModel> roleServiceModels = userServiceModel.getAuthorities();
                    Set<Role> roles = new HashSet<>();
                    for (RoleServiceModel roleServiceModel : roleServiceModels) {
                        Role role = this.roleRepository.findByAuthority(roleServiceModel.getAuthority())
                                .orElseThrow(() -> new NoSuchElementException(String.format("Invalid role: %s", roleServiceModel.getAuthority())));
                        roles.add(role);
                    }

                    //If no exceptions are thrown, create user.
                    User user = this.mapper.map(userServiceModel, User.class);
                    user.setAuthorities(roles);
                    //user.setPassword(this.passwordEncoder.encode(userServiceModel.getPassword()));
                    user.setPassword(userServiceModel.getPassword());
                    this.userRepository.saveAndFlush(user);
                    System.out.println(String.format("User %s successfully added.", user.getUsername()));
                }

                //Skip user and print message for invalid role
                catch (NoSuchElementException ex){
                    System.out.println(String.format("User %s not added, reason: %s", userServiceModel.getUsername(), ex.getMessage()));
                }
            }
        }
    }
}
