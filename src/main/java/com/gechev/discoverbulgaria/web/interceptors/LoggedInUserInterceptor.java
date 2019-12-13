package com.gechev.discoverbulgaria.web.interceptors;

import com.gechev.discoverbulgaria.services.AuthenticatedUserService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Calendar;

@Component
public class LoggedInUserInterceptor implements HandlerInterceptor {

    private final AuthenticatedUserService authenticatedUserService;

    public LoggedInUserInterceptor(AuthenticatedUserService authenticatedUserService){
        this.authenticatedUserService = authenticatedUserService;
    }

    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {

        HttpSession session = request.getSession();

        String username = authenticatedUserService.getUsername();

        if(username.equals("anonymousUser") || session.getAttribute("username") != null){
           return;
        }

        session.setAttribute("username", username);
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        if(hour < 12){
            session.setAttribute("greeting", "Добро утро, ");
        }
        else if (hour <= 19){
            session.setAttribute("greeting", "Добър ден, ");
        }
        else {
            session.setAttribute("greeting", "Добър вечер, ");
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception exception) throws Exception {
    }
}
