package com.sss.member;

import com.sss.MemberAuth;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginTestController {

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/user-page")
    public Authentication user(@AuthenticationPrincipal MemberAuth.Main currentMember) {
        log.debug("### currentUser: {}", currentMember);
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/admin-page")
    public Authentication admin(@AuthenticationPrincipal MemberAuth.Main currentMember) {
        log.debug("### currentUser: {}", currentMember);
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
