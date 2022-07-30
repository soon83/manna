package com.sss.member;

import com.sss.category.CategoryDto;
import com.sss.domain.member.Member;
import com.sss.domain.member.MemberCommand;
import com.sss.domain.member.MemberQuery;
import com.sss.domain.member.interest.Interest;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class MemberDto {

    /**
     * request
     */
    @Data
    public static class SearchCondition {
        private String memberLoginId;
        private String memberName;
        private String memberEmail;
        private String memberNickName;
        private Member.Status memberStatus;

        public MemberQuery.SearchConditionInfo toSearchConditionInfo() {
            return MemberQuery.SearchConditionInfo.builder()
                    .loginId(memberLoginId)
                    .name(memberName)
                    .email(memberEmail)
                    .nickName(memberNickName)
                    .status(memberStatus)
                    .build();
        }
    }

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
        @Email
        @NotBlank(message = "memberEmail 는 필수값입니다.")
        private String memberEmail;
        private String memberAvatar;
        @NotBlank(message = "memberNickName 는 필수값입니다.")
        private String memberNickName;
        @NotBlank(message = "memberSelfIntroduction 는 필수값입니다.")
        private String memberSelfIntroduction;
        @NotNull(message = "memberInterestList 는 필수값입니다.")
        @Size(min = 1, message = "memberInterestList 는 최소 1개 이상이어야 합니다.")
        private List<CreateInterestRequest> memberInterestList;

        public MemberCommand.CreateMember toCreateMemberCommand() {
            return MemberCommand.CreateMember.builder()
                    .loginId(memberLoginId)
                    .loginPassword(memberLoginPassword)
                    .name(memberName)
                    .email(memberEmail)
                    .avatar(memberAvatar)
                    .nickName(memberNickName)
                    .selfIntroduction(memberSelfIntroduction)
                    .build();
        }
    }

    @Data
    public static class CreateInterestRequest {
        private String categoryItemToken;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ModifyRequest {
        @NotBlank(message = "memberName 는 필수값입니다.")
        private String memberName;
        @NotBlank(message = "memberEmail 는 필수값입니다.")
        private String memberEmail;
        private String memberAvatar;
        @NotBlank(message = "memberNickName 는 필수값입니다.")
        private String memberNickName;
        @NotBlank(message = "memberSelfIntroduction 는 필수값입니다.")
        private String memberSelfIntroduction;
        @NotNull(message = "memberInterestList 는 필수값입니다.")
        private List<Interest> memberInterestList;

        public MemberCommand.UpdateMember toUpdateMemberCommand() {
            return MemberCommand.UpdateMember.builder()
                    .name(memberName)
                    .email(memberEmail)
                    .avatar(memberAvatar)
                    .nickName(memberNickName)
                    .selfIntroduction(memberSelfIntroduction)
                    .build();
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ModifyMemberPasswordRequest {
        @NotBlank(message = "memberToken 는 필수값입니다.")
        private String memberToken;
        @NotBlank(message = "memberLoginPassword 는 필수값입니다.")
        private String memberLoginPassword;

        public MemberCommand.UpdateMemberPassword toUpdateMemberPasswordCommand() {
            return MemberCommand.UpdateMemberPassword.builder()
                    .loginPassword(memberLoginPassword)
                    .build();
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ModifyMemberStatusRequest {
        @NotBlank(message = "memberToken 는 필수값입니다.")
        private String memberToken;
    }

    /**
     * response
     */
    @Getter
    @ToString
    @AllArgsConstructor
    public static class MainResponse {
        private final String memberToken;
        private final String memberLoginId;
        private final String memberName;
        private final String memberEmail;
        private final String memberAvatar;
        private final String memberNickName;
        private final String memberSelfIntroduction;
        private final Member.Role memberRole;
        private final Member.Status memberStatus;

        public MainResponse(MemberQuery.Main memberInfo) {
            this.memberToken = memberInfo.getToken();
            this.memberLoginId = memberInfo.getLoginId();
            this.memberName = memberInfo.getName();
            this.memberEmail = memberInfo.getEmail();
            this.memberAvatar = memberInfo.getAvatar();
            this.memberNickName = memberInfo.getNickName();
            this.memberSelfIntroduction = memberInfo.getSelfIntroduction();
            this.memberRole = memberInfo.getRole();
            this.memberStatus = memberInfo.getStatus();
        }
    }

    @Getter
    @ToString
    @AllArgsConstructor
    public static class WithInterestListResponse {
        private final String memberToken;
        private final String memberLoginId;
        private final String memberName;
        private final String memberEmail;
        private final String memberAvatar;
        private final String memberNickName;
        private final String memberSelfIntroduction;
        private final List<InterestResponse> memberInterestList;
        private final Member.Role memberRole;
        private final Member.Status memberStatus;

        public WithInterestListResponse(MemberQuery.WithInterestInfo info, List<InterestResponse> memberInterestList) {
            this.memberToken = info.getToken();
            this.memberLoginId = info.getLoginId();
            this.memberName = info.getName();
            this.memberEmail = info.getEmail();
            this.memberAvatar = info.getAvatar();
            this.memberNickName = info.getNickName();
            this.memberSelfIntroduction = info.getSelfIntroduction();
            this.memberRole = info.getRole();
            this.memberStatus = info.getStatus();
            this.memberInterestList = memberInterestList;
        }
    }

    @Getter
    @ToString
    public static class RegisterResponse {
        private final String memberToken;

        public RegisterResponse(String memberToken) {
            this.memberToken = memberToken;
        }
    }

    @Getter
    @Builder
    @ToString
    @AllArgsConstructor
    public static class InterestResponse {
        private final String interestToken;
        private final CategoryDto.CategoryItemResponse categoryItem;

        public InterestResponse(MemberQuery.InterestInfo info) {
            this.interestToken = info.getToken();
            this.categoryItem = new CategoryDto.CategoryItemResponse(info.getCategoryItemInfo());
        }
    }
}
