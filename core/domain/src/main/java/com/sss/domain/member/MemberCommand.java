package com.sss.domain.member;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

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
        private List<Integer> categoryList;
        private List<Integer> categoryItemList;

        public Member toEntity() {
            return Member.builder()
                    .loginId(loginId)
                    .loginPassword(loginPassword)
                    .name(name)
                    .email(email)
                    .avatar(avatar)
                    .nickName(nickName)
                    .selfIntroduction(selfIntroduction)
                    .categoryList(categoryList)
                    .categoryItemList(categoryItemList)
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
        private List<Integer> categoryList;
        private List<Integer> categoryItemList;
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
