package com.sss.interfaces;

import com.sss.domain.Member;
import com.sss.domain.MemberInfo;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

public class MemberDto {

    /**
     * request
     */
    @Data
    @Builder
    public static class RegisterRequest {

        @NotBlank(message = "memberLoginId 는 필수값입니다.")
        private String memberLoginId;

        @NotBlank(message = "memberLoginPassword 는 필수값입니다.")
        private String memberLoginPassword;

        @NotBlank(message = "memberName 는 필수값입니다.")
        private String memberName;

        @NotBlank(message = "memberEmail 는 필수값입니다.")
        private String memberEmail;
    }

    /**
     * response
     */
    @Getter
    @Builder
    @ToString
    public static class MainResponse {
        private final Long memberId;
        private final String memberToken;
        private final String memberLoginId;
        private final String memberLoginPassword;
        private final String memberName;
        private final String memberEmail;
        private final Member.Status memberStatus;

        public MainResponse(MemberInfo info) {
            this.memberId = info.getId();
            this.memberToken = info.getToken();
            this.memberLoginId = info.getLoginId();
            this.memberLoginPassword = info.getLoginPassword();
            this.memberName = info.getName();
            this.memberEmail = info.getEmail();
            this.memberStatus = info.getStatus();
        }
    }
}
