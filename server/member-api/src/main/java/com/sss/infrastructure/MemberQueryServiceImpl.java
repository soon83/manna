package com.sss.infrastructure;

import com.sss.domain.Member;
import com.sss.domain.MemberQueryService;
import com.sss.exception.MemberNotFoundException;
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
    public Member authMember(String username) {
        return memberRepository.findByLoginId(username)
                .orElseThrow(MemberNotFoundException::new);
    }

    @Override
    public List<Member> getMembers() {
        return memberRepository.findAll();
    }

    @Override
    public Member getMember(String memberToken) {
        return memberRepository.findByToken(memberToken)
                .orElseThrow(MemberNotFoundException::new);
    }
}
