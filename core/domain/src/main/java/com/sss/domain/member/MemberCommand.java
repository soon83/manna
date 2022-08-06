package com.sss.domain.member;

import com.sss.domain.member.interest.Interest;
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
        private String avatar;
        private String nickName;
        private String selfIntroduction;
        private List<CreateInterest> interestList;

        public Member toEntity() {
            return Member.builder()
                    .loginId(loginId)
                    .loginPassword(loginPassword)
                    .name(name)
                    .avatar(avatar)
                    .nickName(nickName)
                    .selfIntroduction(selfIntroduction)
                    .build();
        }

        public Member toEntity(List<Interest> interestList) {
            return Member.builder()
                    .loginId(loginId)
                    .loginPassword(loginPassword)
                    .name(name)
                    .avatar(avatar)
                    .nickName(nickName)
                    .selfIntroduction(selfIntroduction)
                    .interestList(interestList)
                    .build();
        }
    }

    @Getter
    @Builder
    @ToString
    public static class CreateInterest {
        private Long categoryItemId;

        public CreateInterest(Long categoryItemId) {
            this.categoryItemId = categoryItemId;
        }
    }

    @Getter
    @Builder
    @ToString
    public static class UpdateMember {
        private String name;
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
