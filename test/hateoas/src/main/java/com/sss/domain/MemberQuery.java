package com.sss.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class MemberQuery {

    @Getter
    @Builder
    @ToString
    @AllArgsConstructor
    public static class Main {
        private final Long memberId;
        private final String memberName;
        private final Integer memberAge;
        private final Member.Gender memberGender;

        public Main(Member entity) {
            this.memberId = entity.getId();
            this.memberName = entity.getName();
            this.memberAge = entity.getAge();
            this.memberGender = entity.getGender();
        }
    }
}
