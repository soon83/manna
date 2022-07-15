package com.sss.member;

import com.sss.domain.member.MemberCommand;
import com.sss.domain.member.MemberInfo;
import com.sss.domain.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberFacade {

    private final PasswordEncoder passwordEncoder;
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
        registerMemberCommand.setLoginPassword(encodePassword(registerMemberCommand.getLoginPassword()));
        return memberService.registerMember(registerMemberCommand);
    }

    public void changeMember(MemberCommand.ChangeMember changeMemberCommand, String memberToken) {
        changeMemberCommand.setLoginPassword(encodePassword(changeMemberCommand.getLoginPassword()));
        memberService.changeMember(changeMemberCommand, memberToken);
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

    private String encodePassword(String password) {
        if (!ObjectUtils.isEmpty(password)) {
            return passwordEncoder.encode(password);
        }
        return null;
    }
}
