package com.sss.domain.member;

import java.util.List;

public interface MemberQueryService {
    List<Member> readMemberList();
    Member readMember(String memberToken);
    Member readLoginMember(String memberLoginId);
}
