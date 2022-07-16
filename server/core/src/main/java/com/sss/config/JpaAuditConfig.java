package com.sss.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "securityAuditorAware")
public class JpaAuditConfig {
    @Bean
    public SecurityAuditorAware securityAuditorAware() {
        return new SecurityAuditorAware();
    }
}
