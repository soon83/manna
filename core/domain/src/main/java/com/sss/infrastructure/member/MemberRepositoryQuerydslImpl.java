package com.sss.infrastructure.member;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sss.domain.member.Member;
import com.sss.domain.member.MemberQuery;
import com.sss.infrastructure.CustomRepositoryQuerydslSupport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.ObjectUtils;

import static com.sss.domain.member.QMember.member;

public class MemberRepositoryQuerydslImpl extends CustomRepositoryQuerydslSupport implements MemberRepositoryQuerydsl {

    private final JPAQueryFactory queryFactory;

    public MemberRepositoryQuerydslImpl(JPAQueryFactory queryFactory) {
        super(Member.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<Member> findAllMemberList(MemberQuery.SearchConditionInfo condition, Pageable pageable) {
        return applyPagination(pageable, query -> query
                .selectFrom(member)
                .where(
                        eq(member.loginId, condition.getLoginId()),
                        eq(member.name, condition.getName()),
                        eq(member.email, condition.getEmail()),
                        eq(member.nickName, condition.getNickName())
                )
        );
    }

    public static BooleanExpression eq(StringPath domainValue, String value) {
        if (ObjectUtils.isEmpty(value)) return null;
        return domainValue.eq(value);
    }
}