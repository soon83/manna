package com.sss.domain.member;

import com.sss.domain.member.interest.Interest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberQueryService {
    Page<Member> readMemberList(MemberQuery.SearchConditionInfo condition, Pageable pageable);
    List<Member> fetchMemberWithInterestList();
    Member readMember(String memberToken);
    Member readMemberByLoginId(String memberLoginId);
    List<MemberQuery.WithInterestInfo> memberListMapper(List<Member> memberList);
    List<MemberQuery.InterestInfo> memberInterestListMapper(List<Interest> interestList);
}
