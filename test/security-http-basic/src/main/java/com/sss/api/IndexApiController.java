package com.sss.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Slf4j
@RestController
@RequiredArgsConstructor
public class IndexApiController {

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/api/admin")
    public Authentication admin() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/api/user")
    public Authentication user(HttpServletRequest request) {
        log.debug("# request: {}", request);


        return SecurityContextHolder.getContext().getAuthentication();
    }

    @GetMapping("/api/all")
    public UserDetails all(@AuthenticationPrincipal UserDetails currentUser) {
        return currentUser;
    }
}
