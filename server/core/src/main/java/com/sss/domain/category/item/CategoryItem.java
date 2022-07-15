package com.sss.domain.category.item;

import com.sss.TokenGenerator;
import com.sss.domain.BaseEntity;
import com.sss.domain.category.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Slf4j
@Getter
@Entity
@NoArgsConstructor
@Table(name = "manna_category_item", uniqueConstraints = {
        @UniqueConstraint(name = "UK_categoryItem_token", columnNames = {"token"}),
        @UniqueConstraint(name = "UK_categoryItem_title", columnNames = {"title"})
})
public class CategoryItem extends BaseEntity {

    private static final String TOKEN_PREFIX = "ctgItm_";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 31)
    private String token;

    @Column(length = 15)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false, foreignKey = @ForeignKey(name = "FK_categoryItem_category"))
    private Category category;

    @Builder
    public CategoryItem(String title) {
        this.token = TokenGenerator.randomCharacterWithPrefix(TOKEN_PREFIX);
        this.title = title;
    }
}
