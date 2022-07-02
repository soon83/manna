package com.sss.config;

import com.sss.domain.Member;
import com.sss.domain.MemberQueryService;
import com.sss.exception.MemberNotFoundException;
import com.sss.infrastructure.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginQueryServiceImpl implements LoginQueryService {

    private final MemberRepository memberRepository;

    @Override
    public Member getLoginMember(String username) {
        return memberRepository.findByLoginId(username)
                .orElseThrow(MemberNotFoundException::new);
    }
}
