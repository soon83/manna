package com.sss.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberQueryService memberQueryService;
    private final MemberCommandService memberCommandService;

    public MemberQuery.Main registerMember(MemberCommand.Register registerCommand) {
        Member memberEntity = registerCommand.toEntity();
        Member member = memberCommandService.createMember(memberEntity);
        return new MemberQuery.Main(member);
    }

    public Page<MemberQuery.Main> fetchMemberList(Pageable pageable) {
        return memberQueryService.fetchMemberList(pageable)
                .map(MemberQuery.Main::new);
    }

    public MemberQuery.Main fetchMember(Long memberId) {
        return new MemberQuery.Main(memberQueryService.fetchMember(memberId));
    }
}
