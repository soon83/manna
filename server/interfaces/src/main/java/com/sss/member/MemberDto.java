package com.sss.member;

import com.sss.domain.member.Member;
import com.sss.domain.member.MemberCommand;
import com.sss.domain.member.MemberInfo;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

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

        private String memberAvatar;

        @NotBlank(message = "memberNickName 는 필수값입니다.")
        private String memberNickName;

        @NotBlank(message = "memberSelfIntroduction 는 필수값입니다.")
        private String memberSelfIntroduction;

        @NotNull(message = "memberCategoryList 는 필수값입니다.")
        @Size(min = 1, message = "memberCategoryList 는 최소 1개 이상이어야 합니다.")
        private List<Integer> memberCategoryList;

        @NotNull(message = "memberCategoryItemList 는 필수값입니다.")
        @Size(min = 1, message = "memberCategoryItemList 는 최소 1개 이상이어야 합니다.")
        private List<Integer> memberCategoryItemList;

        public MemberCommand.RegisterMember toRegisterMemberCommand() {
            return MemberCommand.RegisterMember.builder()
                    .loginId(memberLoginId)
                    .loginPassword(memberLoginPassword)
                    .name(memberName)
                    .email(memberEmail)
                    .avatar(memberAvatar)
                    .nickName(memberNickName)
                    .selfIntroduction(memberSelfIntroduction)
                    .categoryList(memberCategoryList)
                    .categoryItemList(memberCategoryItemList)
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

        @NotBlank(message = "memberName 는 필수값입니다.")
        private String memberName;

        @NotBlank(message = "memberEmail 는 필수값입니다.")
        private String memberEmail;

        private String memberAvatar;

        @NotBlank(message = "memberNickName 는 필수값입니다.")
        private String memberNickName;

        @NotBlank(message = "memberSelfIntroduction 는 필수값입니다.")
        private String memberSelfIntroduction;

        @NotNull(message = "memberCategoryList 는 필수값입니다.")
        private List<Integer> memberCategoryList;

        @NotNull(message = "memberCategoryItemList 는 필수값입니다.")
        private List<Integer> memberCategoryItemList;

        public MemberCommand.ChangeMember toChangeMemberCommand() {
            return MemberCommand.ChangeMember.builder()
                    .loginId(memberLoginId)
                    .name(memberName)
                    .email(memberEmail)
                    .avatar(memberAvatar)
                    .nickName(memberNickName)
                    .selfIntroduction(memberSelfIntroduction)
                    .categoryList(memberCategoryList)
                    .categoryItemList(memberCategoryItemList)
                    .build();
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChangeMemberPasswordRequest {
        @NotBlank(message = "memberToken 는 필수값입니다.")
        private String memberToken;
        @NotBlank(message = "memberLoginPassword 는 필수값입니다.")
        private String memberLoginPassword;

        public MemberCommand.ChangeMemberPassword toChangeMemberPasswordCommand() {
            return MemberCommand.ChangeMemberPassword.builder()
                    .loginPassword(memberLoginPassword)
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
        private final String memberAvatar;
        private final String memberNickName;
        private final String memberSelfIntroduction;
        private final String memberCategoryList;
        private final String memberCategoryItemList;
        private final Member.Role memberRole;
        private final Member.Status memberStatus;

        public MainResponse(MemberInfo.Main memberInfo) {
            this.memberToken = memberInfo.getToken();
            this.memberLoginId = memberInfo.getLoginId();
            this.memberName = memberInfo.getName();
            this.memberEmail = memberInfo.getEmail();
            this.memberAvatar = memberInfo.getAvatar();
            this.memberNickName = memberInfo.getNickName();
            this.memberSelfIntroduction = memberInfo.getSelfIntroduction();
            this.memberCategoryList = memberInfo.getCategoryList();
            this.memberCategoryItemList = memberInfo.getCategoryItemList();
            this.memberRole = memberInfo.getRole();
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
