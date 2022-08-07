package com.sss.member;

import com.sss.domain.member.MemberCommand;
import com.sss.domain.member.MemberQuery;
import com.sss.domain.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberFacade {

    private final MemberService memberService;

    public Page<MemberQuery.Main> fetchMemberList(MemberQuery.SearchConditionInfo condition, Pageable pageable) {
        return memberService.fetchMemberList(condition, pageable);
    }

    public List<MemberQuery.WithInterestInfo> fetchMemberWithInterestList() {
        return memberService.fetchMemberWithInterestList();
    }

    public MemberQuery.WithInterestInfo fetchMember(String memberToken) {
        return memberService.fetchMember(memberToken);
    }

    public MemberQuery.Main fetchMemberByEmail(String memberEmail) {
        return memberService.fetchMemberByEmail(memberEmail);
    }

    public String registerMember(MemberCommand.CreateMember command) {
        return memberService.registerMember(command);
    }

    public void modifyMember(MemberCommand.UpdateMember command, String memberToken) {
        memberService.modifyMember(command, memberToken);
    }

    public void modifyMemberPassword(MemberCommand.UpdateMemberPassword command, String memberToken) {
        memberService.modifyMemberPassword(command, memberToken);
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
