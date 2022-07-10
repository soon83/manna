package com.sss;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Slf4j
@Getter
@Entity
@NoArgsConstructor
@Table(name = "manna_category")
public class Category {

    private static final String TOKEN_PREFIX = "ctg_";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 31, unique = true)
    private String token;

    @Column(length = 15, unique = true)
    private String title;

    @Builder
    public Category(String title) {
        this.token = TokenGenerator.randomCharacterWithPrefix(TOKEN_PREFIX);
        this.title = title;
    }

    public void updateCategory(String title) {
        this.title = title;
    }
}
