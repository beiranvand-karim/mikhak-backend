package com.example.transportationbackend.util;

import com.example.transportationbackend.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(16);
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService());
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.httpBasic()
                .and()
                .authorizeRequests()
                .mvcMatchers(HttpMethod.POST, "/logIn").authenticated()
                .mvcMatchers(HttpMethod.GET, "/all_roads").permitAll()
                .mvcMatchers(HttpMethod.GET, "/lightposts/*").permitAll()
                .mvcMatchers(HttpMethod.POST, "/register_road").permitAll()
                .mvcMatchers(HttpMethod.POST, "/submit_light_post").permitAll()
                .mvcMatchers(HttpMethod.POST, "/upload_file").permitAll()
                .and()
                .authenticationProvider(authenticationProvider())
                .formLogin().disable()
                .csrf().disable();
        return http.build();
    }
}
