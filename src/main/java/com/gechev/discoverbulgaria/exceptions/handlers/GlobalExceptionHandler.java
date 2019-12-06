package com.gechev.discoverbulgaria.exceptions.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Throwable.class)
    public ModelAndView handleExceptions(Throwable e){
        ModelAndView modelAndView = new ModelAndView("global-error");
        LOGGER.error("Global error occurred!", e);

        return modelAndView;
    }
}