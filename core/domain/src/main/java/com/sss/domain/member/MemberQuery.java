package com.sss.domain.member;

import com.sss.domain.category.CategoryQuery;
import com.sss.domain.member.interest.Interest;
import lombok.*;

import java.util.List;

public class MemberQuery {

    @Data
    @Builder
    @AllArgsConstructor
    public static class SearchConditionInfo {
        private String email;
        private String name;
        private String nickName;
        private Member.Status status;
    }

    @Getter
    @Builder
    @ToString
    @AllArgsConstructor
    public static class Main {
        private final Long id;
        private final String token;
        private final String email;
        private final String password;
        private final String name;
        private final String avatar;
        private final String nickName;
        private final String selfIntroduction;
        private final Member.Role role;
        private final Member.Status status;
        public Main(Member entity) {
            this.id = entity.getId();
            this.token = entity.getToken();
            this.email = entity.getEmail();
            this.password = entity.getPassword();
            this.name = entity.getName();
            this.avatar = entity.getAvatar();
            this.nickName = entity.getNickName();
            this.selfIntroduction = entity.getSelfIntroduction();
            this.role = entity.getRole();
            this.status = entity.getStatus();
        }

    }
    @Getter
    @Builder
    @ToString
    @AllArgsConstructor
    public static class WithInterestInfo {
        private final Long id;
        private final String token;
        private final String email;
        private final String password;
        private final String name;
        private final String avatar;
        private final String nickName;
        private final String selfIntroduction;
        private final List<InterestInfo> interestInfoList;
        private final Member.Role role;
        private final Member.Status status;

        public WithInterestInfo(Member entity, List<InterestInfo> interestInfoList) {
            this.id = entity.getId();
            this.token = entity.getToken();
            this.email = entity.getEmail();
            this.password = entity.getPassword();
            this.name = entity.getName();
            this.avatar = entity.getAvatar();
            this.nickName = entity.getNickName();
            this.selfIntroduction = entity.getSelfIntroduction();
            this.interestInfoList = interestInfoList;
            this.role = entity.getRole();
            this.status = entity.getStatus();
        }
    }

    @Getter
    @Builder
    @ToString
    @AllArgsConstructor
    public static class InterestInfo {
        private final Long id;
        private final String token;
        private final CategoryQuery.CategoryItemInfo categoryItemInfo;

        public InterestInfo(Interest entity) {
            this.id = entity.getId();
            this.token = entity.getToken();
            this.categoryItemInfo = new CategoryQuery.CategoryItemInfo(entity.getCategoryItem());
        }
    }
}
