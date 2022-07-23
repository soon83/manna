package com.sss.web;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class IndexController {

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/admin")
    @ResponseBody
    public Authentication admin() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/user")
    @ResponseBody
    public Authentication user() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @GetMapping("/all")
    @ResponseBody
    public UserDetails all(@AuthenticationPrincipal UserDetails currentUser) {
        return currentUser;
    }
}
