package com.gechev.discoverbulgaria.web.interceptors;

import com.gechev.discoverbulgaria.services.AuthenticatedUserService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class LoggedInUserInterceptor implements HandlerInterceptor {

    private final AuthenticatedUserService authenticatedUserService;

    public LoggedInUserInterceptor(AuthenticatedUserService authenticatedUserService){
        this.authenticatedUserService = authenticatedUserService;
    }

    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) {

        HttpSession session = request.getSession();

        if(session.getAttribute("username") != null){
            return;
        }

        String username = authenticatedUserService.getUsername();
        session.setAttribute("username", username);
    }
}
