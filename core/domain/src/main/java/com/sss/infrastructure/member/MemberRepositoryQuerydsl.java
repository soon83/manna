package com.sss.infrastructure.member;

import com.sss.domain.member.Member;
import com.sss.domain.member.MemberQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberRepositoryQuerydsl {
    Page<Member> findAllMemberList(MemberQuery.SearchConditionInfo condition, Pageable pageable);
}
