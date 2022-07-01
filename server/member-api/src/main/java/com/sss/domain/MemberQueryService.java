package com.sss.domain;

import java.util.List;

public interface MemberQueryService {

    List<Member> getMembers();

    Member getMember(String memberToken);
}
