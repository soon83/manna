package com.sss.infrastructure.member;

import com.sss.domain.member.Member;
import com.sss.domain.member.MemberQueryService;
import com.sss.exception.member.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberQueryServiceImpl implements MemberQueryService {
    private final MemberRepository memberRepository;

    @Override
    public List<Member> getMemberList() {
        return memberRepository.findAll();
    }

    @Override
    public Member getMember(String memberToken) {
        return memberRepository.findByToken(memberToken)
                .orElseThrow(MemberNotFoundException::new);
    }

    @Override
    public Member getLoginMember(String memberLoginId) {
        return memberRepository.findByLoginId(memberLoginId)
                .orElseThrow(MemberNotFoundException::new);
    }
}
