package com.sss.config;

import com.sss.domain.login.LoginInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Slf4j
public class SecurityAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.debug("### authentication: {}", authentication);

        if (authentication == null
                || authentication.getPrincipal().equals("anonymousUser")
                || !authentication.isAuthenticated()) {
            return Optional.ofNullable("SYSTEM");
        }

        var loginInfo = (LoginInfo.Main) authentication.getPrincipal();
        return Optional.ofNullable(loginInfo.getMemberLoginId());
    }
}
