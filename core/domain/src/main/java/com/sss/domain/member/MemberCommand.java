package com.sss.domain.member;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class MemberCommand {

    @Getter
    @Builder
    @ToString
    public static class CreateMember {
        private String loginId;
        @Setter
        private String loginPassword;
        private String name;
        private String email;
        private String avatar;
        private String nickName;
        private String selfIntroduction;

        public Member toEntity() {
            return Member.builder()
                    .loginId(loginId)
                    .loginPassword(loginPassword)
                    .name(name)
                    .email(email)
                    .avatar(avatar)
                    .nickName(nickName)
                    .selfIntroduction(selfIntroduction)
                    .build();
        }
    }

    @Getter
    @Builder
    @ToString
    public static class UpdateMember {
        private String name;
        private String email;
        private String avatar;
        private String nickName;
        private String selfIntroduction;
        private Member.Role role;
    }

    @Getter
    @Builder
    @ToString
    public static class UpdateMemberPassword {
        @Setter
        private String loginPassword;
    }
}
