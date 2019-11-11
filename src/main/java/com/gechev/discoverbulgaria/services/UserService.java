package com.gechev.discoverbulgaria.services;

import com.gechev.discoverbulgaria.services.models.UserServiceModel;

public interface UserService {
    UserServiceModel registerUser(UserServiceModel userServiceModel);
    UserServiceModel findByUsername(String username);
    void seedUsers(UserServiceModel[] userServiceModels);
}
