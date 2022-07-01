package com.sss.infrastructure;

import com.sss.domain.Member;
import com.sss.domain.MemberQueryService;
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
    public List<Member> getMembers() {
        return memberRepository.findAll();
    }

    @Override
    public Member getMember(String memberToken) {
        return memberRepository.findByToken(memberToken)
                .orElseThrow(RuntimeException::new);
    }
}