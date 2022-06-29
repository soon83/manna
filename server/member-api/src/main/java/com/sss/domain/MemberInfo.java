package com.sss.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberInfo {

    private Long id;
    private String loginId;
    private String loginPassword;
    private String name;
    private String email;
    private Member.Status status;

    @Builder
    public MemberInfo(Long id, String loginId, String loginPassword, String name, String email, Member.Status status) {
        this.id = id;
        this.loginId = loginId;
        this.loginPassword = loginPassword;
        this.name = name;
        this.email = email;
        this.status = status;
    }

    public MemberInfo(Member entity) {
        this.id = entity.getId();
        this.loginId = entity.getLoginId();
        this.loginPassword = entity.getLoginPassword();
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.status = entity.getStatus();
    }
}
