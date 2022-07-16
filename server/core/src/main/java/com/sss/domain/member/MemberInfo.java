package com.sss.domain.member;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

public class MemberInfo {

    @Getter
    @ToString
    public static class Main {
        private final String token;
        private final String loginId;
        private final String loginPassword;
        private final String name;
        private final String email;
        private final String avatar;
        private final String nickName;
        private final String selfIntroduction;
        private final String categoryList;
        private final String categoryItemList;
        private final Member.Role role;
        private final Member.Status status;

        @Builder
        public Main(
                String token,
                String loginId,
                String loginPassword,
                String name,
                String email,
                String avatar,
                String nickName,
                String selfIntroduction,
                String categoryList,
                String categoryItemList,
                Member.Role role,
                Member.Status status
        ) {
            this.token = token;
            this.loginId = loginId;
            this.loginPassword = loginPassword;
            this.name = name;
            this.email = email;
            this.avatar = avatar;
            this.nickName = nickName;
            this.selfIntroduction = selfIntroduction;
            this.categoryList = categoryList;
            this.categoryItemList = categoryItemList;
            this.role = role;
            this.status = status;
        }

        public Main(Member entity) {
            this.token = entity.getToken();
            this.loginId = entity.getLoginId();
            this.loginPassword = entity.getLoginPassword();
            this.name = entity.getName();
            this.email = entity.getEmail();
            this.avatar = entity.getAvatar();
            this.nickName = entity.getNickName();
            this.selfIntroduction = entity.getSelfIntroduction();
            this.categoryList = entity.getCategoryList();
            this.categoryItemList = entity.getCategoryItemList();
            this.role = entity.getRole();
            this.status = entity.getStatus();
        }
    }
}
