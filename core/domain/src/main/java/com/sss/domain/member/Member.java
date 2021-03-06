package com.sss.domain.member;

import com.sss.TokenGenerator;
import com.sss.domain.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Getter
@Entity
@NoArgsConstructor
@Table(name = "manna_member",
        indexes = @Index(columnList = "token", name = "IDX_memberToken"),
        uniqueConstraints = {
                @UniqueConstraint(name = "UK_memberToken", columnNames = {"token"}),
                @UniqueConstraint(name = "UK_memberLoginId", columnNames = {"loginId"})
        }
)
public class Member extends BaseEntity {
    private static final String TOKEN_PREFIX = "mbr_";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 31)
    private String token;
    @Column(length = 31)
    private String loginId;
    @Column(length = 127)
    private String loginPassword;
    @Column(length = 31)
    private String name;
    @Column(length = 31)
    private String email;
    @Column(length = 255)
    private String avatar;
    @Column(length = 31)
    private String nickName;
    @Column(length = 1023)
    private String selfIntroduction;
    @Column(length = 255)
    private String categoryList;
    @Column(length = 255)
    private String categoryItemList;
    @Enumerated(EnumType.STRING)
    @Column(length = 15)
    private Role role;
    @Enumerated(EnumType.STRING)
    @Column(length = 15)
    private Status status;

    @Getter
    @RequiredArgsConstructor
    public enum Role {
        ADMIN("관리자"),
        MANAGER("운영자"),
        MEMBER("회원");

        private final String title;

        private static final Map<String, Role> descriptionMap = Collections.unmodifiableMap(Stream.of(values())
                .collect(Collectors.toMap(Role::getTitle, Function.identity())));
        public static Optional<Role> of(String description) {
            return Optional.ofNullable(descriptionMap.get(description));
        }
    }

    @Getter
    @RequiredArgsConstructor
    public enum Status {
        ENABLE("활성화"),
        DISABLE("비활성화");

        private final String title;

        private static final Map<String, Status> descriptionMap = Collections.unmodifiableMap(Stream.of(values())
                .collect(Collectors.toMap(Status::getTitle, Function.identity())));
        public static Optional<Status> of(String description) {
            return Optional.ofNullable(descriptionMap.get(description));
        }
    }

    @Builder
    public Member(
            String loginId,
            String loginPassword,
            String name,
            String email,
            String avatar,
            String nickName,
            String selfIntroduction,
            List<Integer> categoryList,
            List<Integer> categoryItemList
    ) {
        this.token = TokenGenerator.randomCharacterWithPrefix(TOKEN_PREFIX);
        this.loginId = loginId;
        this.loginPassword = loginPassword;
        this.name = name;
        this.email = email;
        this.avatar = avatar;
        this.nickName = nickName;
        this.selfIntroduction = selfIntroduction;
        this.categoryList = categoryList.toString();
        this.categoryItemList = categoryItemList.toString();
        this.role = Role.MEMBER;
        this.status = Status.ENABLE;
    }

    public void updateMember(
            String name,
            String email,
            String avatar,
            String nickName,
            String selfIntroduction,
            List<Integer> categoryList,
            List<Integer> categoryItemList
    ) {
        this.name = name;
        this.email = email;
        this.avatar = avatar;
        this.nickName = nickName;
        this.selfIntroduction = selfIntroduction;
        this.categoryList = categoryList.toString();
        this.categoryItemList = categoryItemList.toString();
    }

    public void updateMemberPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public void enable() {
        this.status = Status.ENABLE;
    }

    public void disable() {
        this.status = Status.DISABLE;
    }
}
