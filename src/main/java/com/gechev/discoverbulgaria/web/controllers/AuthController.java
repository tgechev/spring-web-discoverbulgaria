package com.gechev.discoverbulgaria.web.controllers;

import com.gechev.discoverbulgaria.services.UserService;
import com.gechev.discoverbulgaria.services.models.UserServiceModel;
import com.gechev.discoverbulgaria.web.models.UserRegisterModel;
import com.gechev.discoverbulgaria.web.models.UserViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/users")
public class AuthController extends BaseController {

    private final UserService userService;
    private final ModelMapper mapper;

    public AuthController(UserService userService, ModelMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @ModelAttribute("userRegisterModel")
    public UserRegisterModel model(){
        return new UserRegisterModel();
    }

    @ModelAttribute("userViewModel")
    public UserViewModel viewModel(){
        return new UserViewModel();
    }

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    public ModelAndView getLogin(){
        return super.view("users/login.html");
    }

    @GetMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView getRegister(@ModelAttribute("userRegisterModel") UserRegisterModel model){
        return super.view("users/register.html");
    }

    @PostMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView registerPost(@Valid @ModelAttribute("userRegisterModel") UserRegisterModel model, BindingResult result){
        if(result.hasErrors()){
            return super.view("users/register.html");
        }

        this.userService.registerUser(this.mapper.map(model, UserServiceModel.class));

        return super.redirect("users/login");
    }

    @GetMapping("/roles")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ModelAndView getUsersRoles(@ModelAttribute("userViewModel") UserViewModel userViewModel, ModelAndView modelAndView){
        List<UserViewModel> userViewModels = this.userService.getUserViewModels();
        modelAndView.addObject("userViewModels", userViewModels);
        return super.view("users/roles.html", modelAndView);
    }

    @PostMapping("/roles")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ModelAndView setUsersRoles(@ModelAttribute("userViewModel") UserViewModel userViewModel, ModelAndView modelAndView){

        this.userService.updateUsersRoles(userViewModel);
        List<UserViewModel> userViewModels = this.userService.getUserViewModels();
        modelAndView.addObject("userViewModels", userViewModels);
        modelAndView.addObject("isSuccess", true);
        return super.view("users/roles.html", modelAndView);
    }

    @GetMapping("/json/{username}")
    @ResponseBody
    public UserViewModel getUserViewModel(@PathVariable String username){
        return this.userService.getUserViewModel(username);
    }

    @InitBinder
    private void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}
