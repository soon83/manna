package com.sss.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberQueryService memberQueryService;
    private final MemberCommandService memberCommandService;

    @Override
    @Transactional(readOnly = true)
    public List<MemberInfo.Main> retrieveMembers() {
        return memberQueryService.getMembers().stream()
                .map(MemberInfo.Main::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public MemberInfo.Main retrieveMember(String memberToken) {
        var member = memberQueryService.getMember(memberToken);
        return new MemberInfo.Main(member);
    }

    @Override
    @Transactional
    public String registerMember(MemberCommand.RegisterMember registerMemberCommand) {
        var member = registerMemberCommand.toEntity();
        var createdMember = memberCommandService.saveMember(member);
        return createdMember.getToken();
    }

    @Override
    @Transactional
    public void changeMember(MemberCommand.ChangeMember changeMemberCommand, String memberToken) {
        var member = memberQueryService.getMember(memberToken);
        member.updateMember(
                changeMemberCommand.getLoginId(),
                changeMemberCommand.getLoginPassword(),
                changeMemberCommand.getName(),
                changeMemberCommand.getEmail()
        );
    }

    @Override
    @Transactional
    public void enableMember(String memberToken) {
        var member = memberQueryService.getMember(memberToken);
        member.enable();
    }

    @Override
    @Transactional
    public void disableMember(String memberToken) {
        var member = memberQueryService.getMember(memberToken);
        member.disable();
    }
}