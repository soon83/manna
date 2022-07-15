package com.sss.login;

import com.sss.domain.login.LoginInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginTestController {

    @PreAuthorize("hasAnyRole('MEMBER')")
    @GetMapping("/user-page")
    public LoginInfo.Main user(@AuthenticationPrincipal LoginInfo.Main currentUser) {
        log.debug("### currentUser: {}", currentUser);
        return currentUser;
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/admin-page")
    public LoginInfo.Main admin(@AuthenticationPrincipal LoginInfo.Main currentUser) {
        log.debug("### currentUser: {}", currentUser);
        return currentUser;
    }
}
