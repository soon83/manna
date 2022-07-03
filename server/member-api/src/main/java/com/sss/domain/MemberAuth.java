package com.sss.domain;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

public class MemberAuth {

    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Main implements UserDetails {

        @NotBlank(message = "username 는 필수값입니다.")
        private String username;

        @NotBlank(message = "password 는 필수값입니다.")
        private String password;

        private Set<Authority> authorities;

        private boolean enabled;
        private boolean accountNonExpired;
        private boolean accountNonLocked;
        private boolean credentialsNonExpired;

        public Main(Member member) {
            this.username = member.getLoginId();
            this.password = member.getLoginPassword();
            this.authorities = new HashSet<>();
            authorities.add(new Authority(1L, "ROLE_USER"));
            this.enabled = true;
            accountNonExpired = true;
            accountNonLocked = true;
            credentialsNonExpired = true;
        }
    }

    @Data
    @Builder
    public static class Authority implements GrantedAuthority {

        private Long id;
        private String authority;
    }
}