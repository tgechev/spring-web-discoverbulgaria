package com.gechev.discoverbulgaria.services;

import com.gechev.discoverbulgaria.services.models.UserServiceModel;
import com.gechev.discoverbulgaria.web.models.UserViewModel;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    void updateUsersRoles(UserViewModel userViewModel);
    UserServiceModel registerUser(UserServiceModel userServiceModel);
    UserServiceModel findUserByUserName(String username);
    void seedUsers(UserServiceModel[] userServiceModels);
    UserDetails loadUserByUsername(String s);
    List<UserViewModel> getUserViewModels();
    List<UserServiceModel> findAll();
    UserViewModel getUserViewModel(String username);
    Long getRepositoryCount();
}
