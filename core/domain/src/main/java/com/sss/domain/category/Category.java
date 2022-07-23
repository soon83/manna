package com.sss.domain.category;

import com.sss.TokenGenerator;
import com.sss.domain.BaseEntity;
import com.sss.domain.category.item.CategoryItem;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
@Entity
@NoArgsConstructor
@Table(name = "manna_category",
        indexes = @Index(columnList = "token", name = "IDX_categoryToken"),
        uniqueConstraints = {
                @UniqueConstraint(name = "UK_categoryToken", columnNames = {"token"}),
                @UniqueConstraint(name = "UK_categoryTitle", columnNames = {"title"})
        }
)
public class Category extends BaseEntity {
    private static final String TOKEN_PREFIX = "ctg_";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 31)
    private String token;
    @Column(length = 15)
    private String title;
    private Integer ordering;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category", cascade = CascadeType.PERSIST)
    private final List<CategoryItem> categoryItemList = new ArrayList<>();

    @Builder
    public Category(String title, Integer ordering) {
        this.token = TokenGenerator.randomCharacterWithPrefix(TOKEN_PREFIX);
        this.title = title;
        this.ordering = ordering;
    }

    public void updateCategory(String title, Integer ordering) {
        this.title = title;
        this.ordering = ordering;
    }
}
