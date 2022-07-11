package com.sss.member;

import com.sss.domain.member.Member;
import com.sss.domain.member.MemberCommand;
import com.sss.domain.member.MemberInfo;
import lombok.*;

import javax.validation.constraints.NotBlank;

public class MemberDto {

    /**
     * request
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RegisterRequest {

        @NotBlank(message = "memberLoginId 는 필수값입니다.")
        private String memberLoginId;

        @NotBlank(message = "memberLoginPassword 는 필수값입니다.")
        private String memberLoginPassword;

        @NotBlank(message = "memberName 는 필수값입니다.")
        private String memberName;

        @NotBlank(message = "memberEmail 는 필수값입니다.")
        private String memberEmail;


        public MemberCommand.RegisterMember toRegisterMemberCommand() {
            return MemberCommand.RegisterMember.builder()
                    .loginId(memberLoginId)
                    .loginPassword(memberLoginPassword)
                    .name(memberName)
                    .email(memberEmail)
                    .build();
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChangeRequest {
        @NotBlank(message = "memberLoginId 는 필수값입니다.")
        private String memberLoginId;

        @NotBlank(message = "memberLoginPassword 는 필수값입니다.")
        private String memberLoginPassword;

        @NotBlank(message = "memberName 는 필수값입니다.")
        private String memberName;

        @NotBlank(message = "memberEmail 는 필수값입니다.")
        private String memberEmail;

        public MemberCommand.ChangeMember toChangeMemberCommand() {
            return MemberCommand.ChangeMember.builder()
                    .loginId(memberLoginId)
                    .loginPassword(memberLoginPassword)
                    .name(memberName)
                    .email(memberEmail)
                    .build();
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChangeMemberStatusRequest {

        @NotBlank(message = "memberToken 는 필수값입니다.")
        private String memberToken;
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
        private final Member.Status memberStatus;

        public MainResponse(MemberInfo.Main memberInfo) {
            this.memberToken = memberInfo.getToken();
            this.memberLoginId = memberInfo.getLoginId();
            this.memberName = memberInfo.getName();
            this.memberEmail = memberInfo.getEmail();
            this.memberStatus = memberInfo.getStatus();
        }
    }

    @Getter
    @Builder
    @ToString
    public static class RegisterResponse {

        private final String memberToken;

        public RegisterResponse(String memberToken) {
            this.memberToken = memberToken;
        }
    }
}
