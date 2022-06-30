package com.sss.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberInfo {
    private Long id;
    private String token;
    private String loginId;
    private String loginPassword;
    private String name;
    private String email;
    private Member.Status status;

    public MemberInfo(Member entity) {
        this.id = entity.getId();
        this.token = entity.getToken();
        this.loginId = entity.getLoginId();
        this.loginPassword = entity.getLoginPassword();
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.status = entity.getStatus();
    }
}
