package com.gechev.discoverbulgaria.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class AuthController {

    @GetMapping("/login")
    public String getLogin(){
        return "users/login.html";
    }

    @PostMapping("/login")
    public String login(){
        return "redirect:/users/login";
    }

    @GetMapping("/register")
    public String getRegister(){
        return "users/register.html";
    }

    @PostMapping("/register")
    public String register(){
        return "redirect:/users/register";
    }
}
