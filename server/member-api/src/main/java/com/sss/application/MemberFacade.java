package com.sss.application;

import com.sss.domain.MemberCommand;
import com.sss.domain.MemberInfo;
import com.sss.domain.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberFacade {

    private final MemberService memberService;

    public List<MemberInfo.Main> retrieveMembers() {
        return memberService.retrieveMembers();
    }

    public MemberInfo.Main retrieveMember(String memberToken) {
        return memberService.retrieveMember(memberToken);
    }

    public String registerMember(MemberCommand.RegisterMember registerMemberCommand) {
        return memberService.registerMember(registerMemberCommand);
    }

    public void changeMember(MemberCommand.ChangeMember changeMemberCommand, String memberToken) {
        memberService.changeMember(changeMemberCommand, memberToken);
    }

    public void enableMember(String memberToken) {
        memberService.enableMember(memberToken);
    }

    public void disableMember(String memberToken) {
        memberService.disableMember(memberToken);
    }
}
