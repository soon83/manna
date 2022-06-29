package com.sss.interfaces;

import com.sss.domain.Member;
import com.sss.domain.MemberInfo;
import lombok.Getter;
import lombok.ToString;

public class MemberDto {

    @Getter
    @ToString
    public static class GetResponse {
        private final Long memberId;
        private final String memberLoginId;
        private final String memberLoginPassword;
        private final String memberName;
        private final String memberEmail;
        private final Member.Status memberStatus;

        public GetResponse(MemberInfo info) {
            this.memberId = info.getId();
            this.memberLoginId = info.getLoginId();
            this.memberLoginPassword = info.getLoginPassword();
            this.memberName = info.getName();
            this.memberEmail = info.getEmail();
            this.memberStatus = info.getStatus();
        }
    }
}
