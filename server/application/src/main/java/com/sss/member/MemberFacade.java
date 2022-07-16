package com.sss.member;

import com.sss.domain.member.MemberCommand;
import com.sss.domain.member.MemberInfo;
import com.sss.domain.member.MemberService;
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

    public MemberInfo.Main retrieveLoginMember(String memberLoginId) {
        return memberService.retrieveLoginMember(memberLoginId);
    }

    public String registerMember(MemberCommand.RegisterMember registerMemberCommand) {
        return memberService.registerMember(registerMemberCommand);
    }

    public void changeMember(MemberCommand.ChangeMember changeMemberCommand, String memberToken) {
        memberService.changeMember(changeMemberCommand, memberToken);
    }

    public void changeMemberPassword(MemberCommand.ChangeMemberPassword changeMemberPasswordCommand, String memberToken) {
        memberService.changeMemberPassword(changeMemberPasswordCommand, memberToken);
    }

    public void enableMember(String memberToken) {
        memberService.enableMember(memberToken);
    }

    public void disableMember(String memberToken) {
        memberService.disableMember(memberToken);
    }

    public void deleteMember(String memberToken) {
        memberService.deleteMember(memberToken);
    }
}
