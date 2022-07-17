package com.sss.member;

import com.sss.domain.member.MemberCommand;
import com.sss.domain.member.MemberQuery;
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

    public List<MemberQuery.Main> fetchMemberList() {
        return memberService.fetchMemberList();
    }

    public MemberQuery.Main fetchMember(String memberToken) {
        return memberService.fetchMember(memberToken);
    }

    public String registerMember(MemberCommand.CreateMember createMemberCommand) {
        return memberService.registerMember(createMemberCommand);
    }

    public void modifyMember(MemberCommand.UpdateMember updateMemberCommand, String memberToken) {
        memberService.modifyMember(updateMemberCommand, memberToken);
    }

    public void modifyMemberPassword(MemberCommand.UpdateMemberPassword updateMemberPasswordCommand, String memberToken) {
        memberService.modifyMemberPassword(updateMemberPasswordCommand, memberToken);
    }

    public void enableMember(String memberToken) {
        memberService.enableMember(memberToken);
    }

    public void disableMember(String memberToken) {
        memberService.disableMember(memberToken);
    }

    public void removeMember(String memberToken) {
        memberService.removeMember(memberToken);
    }
}
