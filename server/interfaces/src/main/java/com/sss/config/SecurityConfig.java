package com.sss.config;

import com.sss.domain.login.LoginService;
import com.sss.jwt.JwtCheckFilter;
import com.sss.jwt.JwtLoginFilter;
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
public class SecurityConfig {
    public static final String LOGIN = "/api/v1/login";
    private final LoginService loginService;

    public SecurityConfig(LoginService loginService) {
        this.loginService = loginService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain jwtFilterChain(HttpSecurity http) throws Exception {
        var authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(loginService).passwordEncoder(passwordEncoder());
        var authenticationManager = authenticationManagerBuilder.build();

        var loginFilter = new JwtLoginFilter(authenticationManager);
        var checkFilter = new JwtCheckFilter(authenticationManager, loginService);
        http
                .authenticationManager(authenticationManager)
                .headers(AbstractHttpConfigurer::disable)
                .csrf().disable()
                .authorizeRequests(auth -> auth.mvcMatchers(HttpMethod.POST, LOGIN).permitAll()
                        .mvcMatchers(HttpMethod.GET, "/docs/index.html").permitAll()
                        .mvcMatchers(HttpMethod.GET, "/api/*/code-list", "/api/*/code-list/*").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(checkFilter, BasicAuthenticationFilter.class)
        ;
        return http.build();
    }
}
