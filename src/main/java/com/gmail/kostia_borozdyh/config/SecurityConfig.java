package com.gmail.kostia_borozdyh.config;

import com.gmail.kostia_borozdyh.entity.User;
import com.gmail.kostia_borozdyh.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpSession;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private HttpSession httpSession;

    @Autowired
    public void setUserEnterService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setHttpSession(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/", "/international/*")
                .permitAll()
                .antMatchers("/editUser").authenticated()
                .antMatchers("/adm/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasRole("USER")
                .antMatchers("/man/**").hasRole("MANAGER")
                .antMatchers("/employee/**").hasRole("EMPLOYEE")
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/loginUser")
                .usernameParameter("login")
                .passwordParameter("password")
                .successForwardUrl("/")
                .and()
                .logout()
                .logoutUrl("/logoutUser")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true);
    }

    @EventListener
    public void authentication(AuthenticationSuccessEvent event) {
        UserDetails userDetails = (UserDetails) event.getAuthentication().getPrincipal();
        String login = userDetails.getUsername();
        User user = userService.findByLogin(login);
        httpSession.setAttribute("user", user);
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        authenticationProvider.setUserDetailsService(userService);
        return authenticationProvider;
    }
}
