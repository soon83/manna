package com.sss.domain.login;

import com.sss.domain.member.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotBlank;
import java.util.*;

@Slf4j
public class LoginInfo {

    @Getter
    @ToString
    public static class AccountAdaptor extends User {

        private LoginInfo.Main memberLoginInfo;

        public AccountAdaptor(LoginInfo.Main memberLoginInfo) {
            super(
                    memberLoginInfo.getMemberLoginId(),
                    memberLoginInfo.getMemberLoginPassword(),
                    authorities(memberLoginInfo.getMemberRole().name())
            );
            this.memberLoginInfo = memberLoginInfo;
        }

        public LoginInfo.Main getLoginUser() {
            memberLoginInfo.setMemberLoginPassword("[PROTECTED]");
            return this.memberLoginInfo;
        }


        private static Collection<? extends GrantedAuthority> authorities(String... roles) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            for (String role : roles) {
                addAuthority(authorities, role);
            }
            return authorities;
        }

        private static Collection<? extends GrantedAuthority> authorities(List<String> permissions, String... roles) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            for (String permission : permissions) {
                addAuthority(authorities, permission);
            }
            for (String role : roles) {
                addAuthority(authorities, role);
            }
            return authorities;
        }

        private static void addAuthority(List<GrantedAuthority> authorities, String role) {
            if (role != null && !role.startsWith("ROLE_")) {
                role = "ROLE_" + role;
            }
            authorities.add(new SimpleGrantedAuthority(role));
        }
    }

    @Getter
    @ToString
    public static class Main implements UserDetails {

        private String memberToken;

        @NotBlank(message = "memberLoginId 는 필수값입니다.")
        private String memberLoginId;

        @Setter
        @NotBlank(message = "memberLoginPassword 는 필수값입니다.")
        private String memberLoginPassword;

        private String memberName;

        private String memberEmail;
        private Member.Role memberRole;

        private Member.Status memberStatus;

        public Main(Member member) {
            this.memberToken = member.getToken();
            this.memberLoginId = member.getLoginId();
            this.memberLoginPassword = member.getLoginPassword();
            this.memberName = member.getName();
            this.memberEmail = member.getEmail();
            this.memberRole = member.getRole();
            this.memberStatus = member.getStatus();
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            Set<GrantedAuthority> authoritySet = new HashSet<>();
            authoritySet.add(new SimpleGrantedAuthority(this.memberRole.name()));
            return authoritySet;
        }

        @Override
        public String getPassword() {
            return this.memberLoginPassword;
        }

        @Override
        public String getUsername() {
            return this.memberLoginId;
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}