package com.sss.domain.member;

import com.sss.TokenGenerator;
import com.sss.domain.BaseEntity;
import com.sss.domain.member.interest.Interest;
import com.sss.enumcode.EnumMapperType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.*;
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
    @Enumerated(EnumType.STRING)
    @Column(length = 15)
    private Role role;
    @Enumerated(EnumType.STRING)
    @Column(length = 15)
    private Status status;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member", cascade = CascadeType.PERSIST)
    private List<Interest> interestList = new ArrayList<>();

    @Getter
    @RequiredArgsConstructor
    public enum Role implements EnumMapperType {
        ADMIN("관리자"),
        MANAGER("운영자"),
        MEMBER("회원");

        private final String text;

        private static final Map<String, Role> descriptionMap = Collections.unmodifiableMap(Stream.of(values())
                .collect(Collectors.toMap(Role::getText, Function.identity())));
        public static Optional<Role> of(String description) {
            return Optional.ofNullable(descriptionMap.get(description));
        }

        @Override
        public String getCode() {
            return name();
        }
    }

    @Getter
    @RequiredArgsConstructor
    public enum Status implements EnumMapperType {
        ENABLE("활성화"),
        DISABLE("비활성화");

        private final String text;

        private static final Map<String, Status> descriptionMap = Collections.unmodifiableMap(Stream.of(values())
                .collect(Collectors.toMap(Status::getText, Function.identity())));
        public static Optional<Status> of(String description) {
            return Optional.ofNullable(descriptionMap.get(description));
        }

        @Override
        public String getCode() {
            return name();
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
            String selfIntroduction
    ) {
        this.token = TokenGenerator.randomCharacterWithPrefix(TOKEN_PREFIX);
        this.loginId = loginId;
        this.loginPassword = loginPassword;
        this.name = name;
        this.email = email;
        this.avatar = avatar;
        this.nickName = nickName;
        this.selfIntroduction = selfIntroduction;
        this.role = Role.MEMBER;
        this.status = Status.ENABLE;
    }

    public void updateMember(
            String name,
            String email,
            String avatar,
            String nickName,
            String selfIntroduction
    ) {
        this.name = name;
        this.email = email;
        this.avatar = avatar;
        this.nickName = nickName;
        this.selfIntroduction = selfIntroduction;
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
