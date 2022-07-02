package com.sss.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginApiController {

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/login")
    public void login() {

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/test")
    public void test() {

    }
}
