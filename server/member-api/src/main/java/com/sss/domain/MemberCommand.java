package com.sss.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class MemberCommand {

    @Getter
    @Builder
    @ToString
    public static class RegisterMember {

        private String loginId;
        private String loginPassword;
        private String name;
        private String email;

        public Member toEntity() {
            return Member.builder()
                    .loginId(loginId)
                    .loginPassword(loginPassword)
                    .name(name)
                    .email(email)
                    .build();
        }
    }

    @Getter
    @Builder
    @ToString
    public static class ChangeMember {
        private String loginId;
        private String loginPassword;
        private String name;
        private String email;
    }

    @Getter
    @Builder
    @ToString
    public static class ChangeMemberStatus {
        private Member.Status status;
    }
}
