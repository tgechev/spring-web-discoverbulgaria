package com.gechev.discoverbulgaria.services.impl;

import com.gechev.discoverbulgaria.constants.Constants;
import com.gechev.discoverbulgaria.data.models.Role;
import com.gechev.discoverbulgaria.data.models.User;
import com.gechev.discoverbulgaria.data.repositories.RoleRepository;
import com.gechev.discoverbulgaria.data.repositories.UserRepository;
import com.gechev.discoverbulgaria.services.RoleService;
import com.gechev.discoverbulgaria.services.UserService;
import com.gechev.discoverbulgaria.services.ValidationService;
import com.gechev.discoverbulgaria.services.models.RoleServiceModel;
import com.gechev.discoverbulgaria.services.models.UserServiceModel;
import com.gechev.discoverbulgaria.web.models.UserViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final ValidationService validationService;
    private final RoleService roleService;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(ValidationService validationService, RoleService roleService, RoleRepository roleRepository, UserRepository userRepository, ModelMapper mapper, BCryptPasswordEncoder passwordEncoder) {
        this.validationService = validationService;
        this.roleService = roleService;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserServiceModel registerUser(UserServiceModel userServiceModel) {

        if(this.userRepository.count() == 0){
            userServiceModel.setAuthorities(this.roleService.findAllRoles());
        }

        else{
            userServiceModel.setAuthorities(new LinkedHashSet<>());
            userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_USER"));
        }

        User user = this.mapper.map(userServiceModel, User.class);
        user.setPassword(this.passwordEncoder.encode(userServiceModel.getPassword()));

        return this.mapper.map(this.userRepository.saveAndFlush(user), UserServiceModel.class);
    }

    @Override
    public UserServiceModel findUserByUserName(String username) {
        return this.userRepository.findByUsername(username)
                .map(u -> this.mapper.map(u, UserServiceModel.class))
                .orElseThrow(() -> new UsernameNotFoundException(Constants.USERNAME_NOT_FOUND));
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return this.userRepository
                .findByUsername(s)
                .orElseThrow(() -> new UsernameNotFoundException(Constants.USERNAME_NOT_FOUND));
    }

    @Override
    public void seedUsers(UserServiceModel[] userServiceModels) {
        for (UserServiceModel userServiceModel : userServiceModels) {

            //Validate user model and print message if invalid
            if(!this.validationService.isValid(userServiceModel)){
                this.validationService.violations(userServiceModel)
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
                    Set<Role> roles = new LinkedHashSet<>();
                    for (RoleServiceModel roleServiceModel : roleServiceModels) {
                        Role role = this.roleRepository.findByAuthority(roleServiceModel.getAuthority())
                                .orElseThrow(() -> new NoSuchElementException(String.format("Invalid role: %s", roleServiceModel.getAuthority())));
                        roles.add(role);
                    }

                    //If no exceptions are thrown, create user.
                    User user = this.mapper.map(userServiceModel, User.class);
                    user.setAuthorities(roles);
                    user.setPassword(this.passwordEncoder.encode(userServiceModel.getPassword()));
                    //user.setPassword(userServiceModel.getPassword());
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

    @Override
    public List<UserViewModel> getUserViewModels() {
        return this.findAll()
                .stream()
                .map(serviceModel -> this.mapper.map(serviceModel, UserViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserServiceModel> findAll() {
        return this.userRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(User::getUsername))
                .map(user -> this.mapper.map(user, UserServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserViewModel getUserViewModel(String username) {
        UserServiceModel serviceModel = findUserByUserName(username);
        UserViewModel userViewModel = this.mapper.map(serviceModel, UserViewModel.class);
        RoleServiceModel adminRole = this.roleService.findByAuthority("ROLE_ADMIN");
        if(serviceModel.getAuthorities().contains(adminRole)){
            userViewModel.setAdmin(true);
        }
        else{
            userViewModel.setAdmin(false);
        }

        return userViewModel;
    }

    @Override
    public void updateUsersRoles(UserViewModel userViewModel) {
        RoleServiceModel adminRole = this.roleService.findByAuthority("ROLE_ADMIN");
        UserServiceModel serviceModel = this.findUserByUserName(userViewModel.getUsername());
        if(userViewModel.isAdmin()){
            serviceModel.getAuthorities().add(adminRole);
        }
        else {
            serviceModel.getAuthorities().remove(adminRole);
        }

        this.userRepository.save(this.mapper.map(serviceModel, User.class));
    }

    @Override
    public Long getRepositoryCount(){
        return this.userRepository.count();
    }
}
