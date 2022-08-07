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
        private String memberEmail;
        private String memberName;
        private String memberNickName;
        private Member.Status memberStatus;

        public MemberQuery.SearchConditionInfo toSearchConditionInfo() {
            return MemberQuery.SearchConditionInfo.builder()
                    .email(memberEmail)
                    .name(memberName)
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
        @Email
        @NotBlank(message = "memberEmail 는 필수값입니다.")
        private String memberEmail;
        @NotBlank(message = "memberPassword 는 필수값입니다.")
        private String memberPassword;
        @NotBlank(message = "memberName 는 필수값입니다.")
        private String memberName;
        private String memberAvatar;
        @NotBlank(message = "memberNickName 는 필수값입니다.")
        private String memberNickName;
        @NotBlank(message = "memberSelfIntroduction 는 필수값입니다.")
        private String memberSelfIntroduction;
        @NotNull(message = "memberInterestList 는 필수값입니다.")
        @Size(min = 1, message = "memberInterestList 는 최소 1개 이상이어야 합니다.")
        private List<CreateInterestRequest> memberInterestList;

        public MemberCommand.CreateMember toCreateMemberCommand(List<MemberCommand.CreateInterest> interestList) {
            return MemberCommand.CreateMember.builder()
                    .email(memberEmail)
                    .password(memberPassword)
                    .name(memberName)
                    .avatar(memberAvatar)
                    .nickName(memberNickName)
                    .selfIntroduction(memberSelfIntroduction)
                    .interestList(interestList)
                    .build();
        }
    }

    @Data
    public static class CreateInterestRequest {
        private Long categoryItemId;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ModifyRequest {
        @Email
        @NotBlank(message = "memberName 는 필수값입니다.")
        private String memberName;
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
        @NotBlank(message = "memberPassword 는 필수값입니다.")
        private String memberPassword;

        public MemberCommand.UpdateMemberPassword toUpdateMemberPasswordCommand() {
            return MemberCommand.UpdateMemberPassword.builder()
                    .password(memberPassword)
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
        private final String memberEmail;
        private final String memberName;
        private final String memberAvatar;
        private final String memberNickName;
        private final String memberSelfIntroduction;
        private final Member.Role memberRole;
        private final Member.Status memberStatus;

        public MainResponse(MemberQuery.Main memberInfo) {
            this.memberToken = memberInfo.getToken();
            this.memberEmail = memberInfo.getEmail();
            this.memberName = memberInfo.getName();
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
        private final String memberEmail;
        private final String memberName;
        private final String memberAvatar;
        private final String memberNickName;
        private final String memberSelfIntroduction;
        private final List<InterestResponse> memberInterestList;
        private final Member.Role memberRole;
        private final Member.Status memberStatus;

        public WithInterestListResponse(MemberQuery.WithInterestInfo info, List<InterestResponse> memberInterestList) {
            this.memberToken = info.getToken();
            this.memberEmail = info.getEmail();
            this.memberName = info.getName();
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
