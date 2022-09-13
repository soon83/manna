package com.sss.interfaces;

import com.sss.domain.Member;
import com.sss.domain.MemberCommand;
import com.sss.domain.MemberQuery;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class MemberDto {

    /**
     * 회원 등록 요청
     */
    @Data
    public static class RegisterRequest {
        @NotBlank
        private String memberName;

        @NotNull
        private Integer memberAge;

        @NotNull
        private Member.Gender memberGender;

        public MemberCommand.Register toRegisterCommand() {
            return MemberCommand.Register.builder()
                    .memberName(memberName)
                    .memberAge(memberAge)
                    .memberGender(memberGender)
                    .build();
        }
    }

    /**
     * 회원 등록 응답
     */
    @Getter
    @Builder
    @ToString
    @AllArgsConstructor
    public static class RegisterResponse extends RepresentationModel<MemberDto.RegisterResponse> {
        private final Long memberId;

        public RegisterResponse(MemberQuery.Main dto) {
            this.memberId = dto.getMemberId();
        }
    }

    /**
     * 회원 응답
     */
    @Getter
    @Builder
    @ToString
    @AllArgsConstructor
    public static class MainResponse extends RepresentationModel<MemberDto.MainResponse> {
        private final Long memberId;
        private final String memberName;
        private final Integer memberAge;
        private final Member.Gender memberGender;

        public MainResponse(MemberQuery.Main dto) {
            this.memberId = dto.getMemberId();
            this.memberName = dto.getMemberName();
            this.memberAge = dto.getMemberAge();
            this.memberGender = dto.getMemberGender();
        }
    }
}
