package com.sss.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sss.domain.login.LoginService;
import com.sss.exception.CustomAuthenticationEntryPoint;
import com.sss.jwt.JwtCheckFilter;
import com.sss.jwt.JwtLoginFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
    public static final String LOGIN = "/api/v1/login";
    private final LoginService loginService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain jwtFilterChain(HttpSecurity http) throws Exception {
        var authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(loginService).passwordEncoder(passwordEncoder());
        var authenticationManager = authenticationManagerBuilder.build();

        var objectMapper = new ObjectMapper().setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);
        var loginFilter = new JwtLoginFilter(authenticationManager, objectMapper);
        var checkFilter = new JwtCheckFilter(authenticationManager, loginService, objectMapper);
        var authenticationEntryPoint = new CustomAuthenticationEntryPoint(objectMapper);

        http
                .authenticationManager(authenticationManager)
                .headers(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeRequests(auth -> auth
                        .mvcMatchers(HttpMethod.POST, LOGIN).permitAll()
                        .mvcMatchers(HttpMethod.GET, LOGIN + "/**").permitAll()
                        .mvcMatchers(HttpMethod.GET, "/docs/index.html").permitAll()
                        .mvcMatchers(HttpMethod.GET, "/api/*/code", "/api/*/code/*").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(handler -> handler.authenticationEntryPoint(authenticationEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(checkFilter, BasicAuthenticationFilter.class)
        ;
        return http.build();
    }
}
