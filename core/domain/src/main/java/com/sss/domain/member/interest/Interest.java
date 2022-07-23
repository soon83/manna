package com.sss.domain.member.interest;

import com.sss.TokenGenerator;
import com.sss.domain.BaseEntity;
import com.sss.domain.category.item.CategoryItem;
import com.sss.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Slf4j
@Getter
@Entity
@NoArgsConstructor
@Table(name = "manna_member_interest",
        indexes = @Index(columnList = "token", name = "IDX_interestToken"),
        uniqueConstraints = {
                @UniqueConstraint(name = "UK_interestToken", columnNames = {"token"})
        }
)
public class Interest extends BaseEntity {
    private static final String TOKEN_PREFIX = "itrt_";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 31)
    private String token;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryItemId", nullable = false, foreignKey = @ForeignKey(name = "FK_interest_categoryItem"))
    private CategoryItem categoryItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId", nullable = false, foreignKey = @ForeignKey(name = "FK_interest_member"))
    private Member member;

    public void setMember(Member member) {
        if (this.member != null) {
            this.member.getInterestList().remove(this); // 기존 것이 존재하면 삭제
        }
        this.member = member;
        member.getInterestList().add(this);
    }

    @Builder
    public Interest(CategoryItem categoryItem, Member member) {
        this.token = TokenGenerator.randomCharacterWithPrefix(TOKEN_PREFIX);
        this.categoryItem = categoryItem;
        this.member = member;
    }
}
