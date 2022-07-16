package com.sss.domain.login;

import com.sss.domain.member.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
    public static class Main {
        private final String memberToken;
        private final String memberLoginId;
        private final String memberLoginPassword;
        private final String memberName;
        private final String memberEmail;
        private final String memberAvatar;
        private final String memberNickName;
        private final String memberSelfIntroduction;
        private final String memberCategoryList;
        private final String memberCategoryItemList;
        private final Member.Role memberRole;
        private final Member.Status memberStatus;

        public Main(Member member) {
            this.memberToken = member.getToken();
            this.memberLoginId = member.getLoginId();
            this.memberLoginPassword = member.getLoginPassword();
            this.memberName = member.getName();
            this.memberEmail = member.getEmail();
            this.memberAvatar = member.getAvatar();
            this.memberNickName = member.getNickName();
            this.memberSelfIntroduction = member.getSelfIntroduction();
            this.memberCategoryList = member.getCategoryList();
            this.memberCategoryItemList = member.getCategoryItemList();
            this.memberRole = member.getRole();
            this.memberStatus = member.getStatus();
        }
    }
}