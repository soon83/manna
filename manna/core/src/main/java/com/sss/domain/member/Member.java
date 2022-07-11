package com.sss.domain.member;

import com.sss.TokenGenerator;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Getter
@Entity
@NoArgsConstructor
@Table(name = "manna_member", uniqueConstraints = {
        @UniqueConstraint(name = "UK_member_token", columnNames = {"token"}),
        @UniqueConstraint(name = "UK_member_loginId", columnNames = {"loginId"})
})
public class Member {

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

    @Enumerated(EnumType.STRING)
    @Column(length = 15)
    private Status status;

    @Getter
    @RequiredArgsConstructor
    public enum Status {

        ENABLE("활성화"),
        DISABLE("비활성화");

        private final String title;

        private static final Map<String, Status> descriptionMap = Collections
                .unmodifiableMap(Stream.of(values())
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
            String email
    ) {
        this.token = TokenGenerator.randomCharacterWithPrefix(TOKEN_PREFIX);
        this.loginId = loginId;
        this.loginPassword = loginPassword;
        this.name = name;
        this.email = email;
        this.status = Status.ENABLE;
    }

    public void updateMember(
            String loginId,
            String loginPassword,
            String name,
            String email
    ) {
        this.loginId = loginId;
        this.loginPassword = loginPassword;
        this.name = name;
        this.email = email;
    }

    public void enable() {
        this.status = Status.ENABLE;
    }

    public void disable() {
        this.status = Status.DISABLE;
    }
}
