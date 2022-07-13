package com.sss.login;

import com.sss.domain.login.CurrentUser;
import com.sss.domain.login.LoginInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginTestController {

    @PreAuthorize("hasAnyRole('MEMBER')")
    @GetMapping("/user-page")
    public Authentication user(@CurrentUser LoginInfo.Main currentUser) {
        log.debug("### currentUser: {}", currentUser);
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @PreAuthorize("hasAnyRole('MEMBER')")
    @GetMapping("/admin-page")
    public UserDetails admin(@AuthenticationPrincipal UserDetails currentUser) {
        log.debug("### currentUser: {}", SecurityContextHolder.getContext().getAuthentication());
        return currentUser;
    }
}
