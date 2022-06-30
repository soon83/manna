package com.sss.domain;

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
@Table(name = "manna_member")
public class Member {

    private final String TOKEN_PREFIX = "mbr_";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 31, unique = true)
    private String token;

    @Column(length = 31, unique = true)
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
    public Member(String name, String email) {
        this.name = name;
        this.email = email;
        this.status = Status.ENABLE;
    }

    public void enable() {
        this.status = Status.ENABLE;
    }

    public void disable() {
        this.status = Status.DISABLE;
    }
}
