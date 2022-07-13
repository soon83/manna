package com.sss.domain.member;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class MemberInfo {

    @Getter
    @ToString
    public static class Main {
        private final Long id;
        private final String token;
        private final String loginId;
        private final String loginPassword;
        private final String name;
        private final String email;
        private final Member.Role role;
        private final Member.Status status;

        @Builder
        public Main(
                Long id,
                String token,
                String loginId,
                String loginPassword,
                String name,
                String email,
                Member.Role role,
                Member.Status status
        ) {
            this.id = id;
            this.token = token;
            this.loginId = loginId;
            this.loginPassword = loginPassword;
            this.name = name;
            this.email = email;
            this.role = role;
            this.status = status;
        }

        public Main(Member entity) {
            this.id = entity.getId();
            this.token = entity.getToken();
            this.loginId = entity.getLoginId();
            this.loginPassword = entity.getLoginPassword();
            this.name = entity.getName();
            this.email = entity.getEmail();
            this.role = entity.getRole();
            this.status = entity.getStatus();
        }
    }
}
