package com.gechev.discoverbulgaria.web.controllers;

import com.gechev.discoverbulgaria.services.UserService;
import com.gechev.discoverbulgaria.web.models.UserViewModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/all")
  @PreAuthorize("hasRole('ROLE_ROOT')")
  public List<UserViewModel> getUsers() {
    return this.userService.getUserViewModels();
  }

  @PostMapping("/edit")
  @PreAuthorize("hasRole('ROLE_ROOT')")
  public ResponseEntity<UserViewModel> setUserRole(@RequestBody UserViewModel userViewModel){

    UserViewModel editedUser = this.userService.updateUserRoles(userViewModel);
    if (editedUser != null && editedUser.getUsername() != null) {
      return ResponseEntity.ok(editedUser);
    }
    return ResponseEntity.status(500).body(userViewModel);
  }
}
