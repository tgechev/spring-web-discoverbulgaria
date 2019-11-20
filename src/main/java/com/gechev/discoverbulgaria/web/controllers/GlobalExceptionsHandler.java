package com.gechev.discoverbulgaria.web.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionsHandler {

    @ExceptionHandler(Throwable.class)
    public String handleExceptions(){
        return "error";
    }
}
