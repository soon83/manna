package com.sss.domain;

import lombok.Builder;
import lombok.Data;

public class MemberCommand {

    @Data
    @Builder
    public static class Register {
        private String memberName;
        private Integer memberAge;
        private Member.Gender memberGender;

        public Member toEntity() {
            return Member.builder()
                    .name(memberName)
                    .age(memberAge)
                    .gender(memberGender)
                    .build();
        }
    }
}
