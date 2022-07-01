package com.sss.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberQueryService memberQueryService;
    private final MemberCommandService memberCommandService;

    @Override
    @Transactional(readOnly = true)
    public List<MemberInfo.Main> retrieveMembers() {
        return memberQueryService.getMembers();
    }

    @Override
    @Transactional
    public String registerMember(MemberCommand.RegisterMember registerMemberCommand) {
        var member = registerMemberCommand.toEntity();
        var createdMember = memberCommandService.saveMember(member);
        return createdMember.getToken();
    }
}
