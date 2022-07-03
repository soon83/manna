package com.sss.domain;

import java.util.List;

public interface MemberQueryService {

    Member authMember(String username);
    List<Member> getMembers();
    Member getMember(String memberToken);

}
