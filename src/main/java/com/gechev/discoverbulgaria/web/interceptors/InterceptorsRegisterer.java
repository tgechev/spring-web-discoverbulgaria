package com.gechev.discoverbulgaria.web.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class InterceptorsRegisterer implements WebMvcConfigurer {

    @Autowired
    LoggedInUserInterceptor loggedInUserInterceptor;

    @Autowired
    GreetingInterceptor greetingInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggedInUserInterceptor).addPathPatterns("/home");
        // registry.addInterceptor(greetingInterceptor).addPathPatterns("/home", "/facts/**", "/poi/**", "/regions/**", "/users/roles");
    }
}
