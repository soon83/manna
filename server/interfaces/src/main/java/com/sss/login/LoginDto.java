package com.sss.login;

import com.sss.domain.login.LoginInfo;
import com.sss.domain.member.Member;
import lombok.*;

import javax.validation.constraints.NotBlank;

public class LoginDto {

    /**
     * request
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginRequest {
        @NotBlank(message = "memberLoginId 는 필수값입니다.")
        private String memberLoginId;
        @NotBlank(message = "memberLoginPassword 는 필수값입니다.")
        private String memberLoginPassword;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CheckLoginIdRequest {
        @NotBlank(message = "memberLoginId 는 필수값입니다.")
        private String memberLoginId;
    }

    /**
     * response
     */
    @Getter
    @ToString
    public static class MainResponse {
        private final String memberToken;
        private final String memberLoginId;
        private final String memberName;
        private final String memberAvatar;
        private final String memberNickName;
        private final String memberSelfIntroduction;
        private final Member.Role memberRole;
        private final Member.Status memberStatus;

        public MainResponse(LoginInfo.Main memberLoginInfo) {
            this.memberToken = memberLoginInfo.getMemberToken();
            this.memberLoginId = memberLoginInfo.getMemberLoginId();
            this.memberName = memberLoginInfo.getMemberName();
            this.memberAvatar = memberLoginInfo.getMemberAvatar();
            this.memberNickName = memberLoginInfo.getMemberNickName();
            this.memberSelfIntroduction = memberLoginInfo.getMemberSelfIntroduction();
            this.memberRole = memberLoginInfo.getMemberRole();
            this.memberStatus = memberLoginInfo.getMemberStatus();
        }
    }
}
