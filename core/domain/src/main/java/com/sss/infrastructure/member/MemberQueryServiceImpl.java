package com.sss.infrastructure.member;

import com.sss.domain.member.Member;
import com.sss.domain.member.MemberQuery;
import com.sss.domain.member.MemberQueryService;
import com.sss.domain.member.interest.Interest;
import com.sss.exception.member.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberQueryServiceImpl implements MemberQueryService {
    private final MemberRepository memberRepository;

    @Override
    public Page<Member> readMemberList(MemberQuery.SearchConditionInfo condition, Pageable pageable) {
        return memberRepository.findAllMemberList(condition, pageable);
    }

    @Override
    public List<Member> fetchMemberWithInterestList() {
        return memberRepository.findAll();
    }

    @Override
    public Member readMember(String memberToken) {
        return memberRepository.findByToken(memberToken)
                .filter(member -> member.getStatus() == Member.Status.ENABLE)
                .orElseThrow(MemberNotFoundException::new);
    }

    @Override
    public Member readMemberByEmail(String memberEmail) {
        return memberRepository.findByEmail(memberEmail)
                .filter(member -> member.getStatus() == Member.Status.ENABLE)
                .orElseThrow(MemberNotFoundException::new);
    }

    @Override
    public List<MemberQuery.WithInterestInfo> memberListMapper(List<Member> memberList) {
        return memberList.stream()
                .map(member -> {
                    var memberInterestList = member.getInterestList();
                    var memberInterestInfoList = memberInterestListMapper(memberInterestList);
                    return new MemberQuery.WithInterestInfo(member, memberInterestInfoList);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<MemberQuery.InterestInfo> memberInterestListMapper(List<Interest> interestList) {
        return interestList.stream()
                .map(MemberQuery.InterestInfo::new)
                .collect(Collectors.toList());
    }
}
