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

    public List<MemberInfo.Main> fetchMemberList() {
        return memberService.fetchMemberList();
    }

    public MemberInfo.Main fetchMember(String memberToken) {
        return memberService.fetchMember(memberToken);
    }

    public String createMember(MemberCommand.CreateMember createMemberCommand) {
        return memberService.createMember(createMemberCommand);
    }

    public void updateMember(MemberCommand.UpdateMember updateMemberCommand, String memberToken) {
        memberService.updateMember(updateMemberCommand, memberToken);
    }

    public void updateMemberPassword(MemberCommand.UpdateMemberPassword updateMemberPasswordCommand, String memberToken) {
        memberService.updateMemberPassword(updateMemberPasswordCommand, memberToken);
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
