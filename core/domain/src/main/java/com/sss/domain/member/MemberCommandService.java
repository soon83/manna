package com.sss.domain.member;

import com.sss.domain.member.interest.Interest;

public interface MemberCommandService {
    Member create(Member member);
    Interest create(Interest interest);
    void delete(Member member);
}
