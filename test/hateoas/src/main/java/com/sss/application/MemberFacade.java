package com.sss.application;

import com.sss.domain.MemberCommand;
import com.sss.domain.MemberQuery;
import com.sss.domain.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberFacade {

    private final MemberService memberService;

    public MemberQuery.Main registerMember(MemberCommand.Register registerCommand) {
        return memberService.registerMember(registerCommand);
    }

    public Page<MemberQuery.Main> fetchMemberList(Pageable pageable) {
        return memberService.fetchMemberList(pageable);
    }

    public MemberQuery.Main fetchMember(Long memberId) {
        return memberService.fetchMember(memberId);
    }
}
