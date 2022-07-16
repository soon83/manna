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
    public static class AuthRequest {

        @NotBlank(message = "memberLoginId 는 필수값입니다.")
        private String memberLoginId;

        @NotBlank(message = "memberLoginPassword 는 필수값입니다.")
        private String memberLoginPassword;
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
        private final String memberEmail;
        private final String memberAvatar;
        private final String memberNickName;
        private final String memberSelfIntroduction;
        private final String memberCategoryList;
        private final String memberCategoryItemList;
        private final Member.Role memberRole;
        private final Member.Status memberStatus;

        public MainResponse(LoginInfo.Main memberLoginInfo) {
            this.memberToken = memberLoginInfo.getMemberToken();
            this.memberLoginId = memberLoginInfo.getMemberLoginId();
            this.memberName = memberLoginInfo.getMemberName();
            this.memberEmail = memberLoginInfo.getMemberEmail();
            this.memberAvatar = memberLoginInfo.getMemberAvatar();
            this.memberNickName = memberLoginInfo.getMemberNickName();
            this.memberSelfIntroduction = memberLoginInfo.getMemberSelfIntroduction();
            this.memberCategoryList = memberLoginInfo.getMemberCategoryList();
            this.memberCategoryItemList = memberLoginInfo.getMemberCategoryItemList();
            this.memberRole = memberLoginInfo.getMemberRole();
            this.memberStatus = memberLoginInfo.getMemberStatus();
        }
    }
}
