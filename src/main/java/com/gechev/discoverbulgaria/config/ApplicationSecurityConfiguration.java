package com.gechev.discoverbulgaria.config;

import com.gechev.discoverbulgaria.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/index.html", "/", "/home", "/login").permitAll()
                .anyRequest().authenticated().and().csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//            .cors().disable()
//            .csrf().disable()
//            .authorizeRequests()
//            .antMatchers("/js/**", "/css/**", "/font/**").permitAll()
//            .antMatchers("/", "/users/register", "/users/login").anonymous()
//            .anyRequest().authenticated()
//            .and()
//            .formLogin()
//            .loginPage("/users/login")
//            .usernameParameter("username")
//            .passwordParameter("password")
//            .defaultSuccessUrl("/home", true)
//            .and()
//            .logout()
//                .deleteCookies("JSESSIONID")
//            .logoutSuccessUrl("/users/login?logout").permitAll();
//    }

    @Bean
    @Override
    public UserDetailsService userDetailsService(){ return userService; }

    @Bean
    public PasswordEncoder passwordEncoder() { return passwordEncoder; }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder);
    }
}
