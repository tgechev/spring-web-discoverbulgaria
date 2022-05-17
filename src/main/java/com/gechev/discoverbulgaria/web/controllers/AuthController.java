package com.gechev.discoverbulgaria.web.controllers;

import com.gechev.discoverbulgaria.dto.ResponseData;
import com.gechev.discoverbulgaria.services.UserService;
import com.gechev.discoverbulgaria.services.models.UserServiceModel;
import com.gechev.discoverbulgaria.web.models.UserRegisterModel;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class AuthController {
  private final UserService userService;
  private final ModelMapper mapper;

  public AuthController(UserService userService, ModelMapper mapper) {
    this.userService = userService;
    this.mapper = mapper;
  }
  @RequestMapping("/user")
  public Principal user(Principal user) {
    return user;
  }

  @PostMapping("/register")
  public ResponseEntity<ResponseData> register(@RequestBody UserRegisterModel registerModel) {
    this.userService.registerUser(this.mapper.map(registerModel, UserServiceModel.class));
    return ResponseEntity.ok(new ResponseData("200", "User registered."));
  }
}
