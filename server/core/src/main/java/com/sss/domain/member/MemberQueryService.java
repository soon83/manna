package com.sss.domain.member;

import java.util.List;

public interface MemberQueryService {
    List<Member> getMemberList();
    Member getMember(String memberToken);
    Member getLoginMember(String memberLoginId);
}
