package com.gmail.kostia_borozdyh.config;

import com.gmail.kostia_borozdyh.service.impl.UserEnterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private UserEnterService userEnterService;

    @Autowired
    public void setUserEnterService(UserEnterService userEnterService) {
        this.userEnterService = userEnterService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
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

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userEnterService);
        return authenticationProvider;
    }
}