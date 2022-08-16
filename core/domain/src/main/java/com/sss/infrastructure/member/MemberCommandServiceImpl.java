package com.sss.infrastructure.member;

import com.sss.domain.member.Member;
import com.sss.domain.member.MemberCommandService;
import com.sss.domain.member.interest.Interest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberCommandServiceImpl implements MemberCommandService {
    private final MemberRepository memberRepository;
    private final MemberInterestRepository memberInterestRepository;

    @Override
    public Member create(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public Interest create(Interest interest) {
        return memberInterestRepository.save(interest);
    }

    @Override
    public void delete(Member member) {
        memberRepository.delete(member);
    }
}
