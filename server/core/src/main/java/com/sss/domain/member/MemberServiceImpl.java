package com.sss.domain.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberQueryService memberQueryService;
    private final MemberCommandService memberCommandService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<MemberInfo.Main> retrieveMemberList() {
        return memberQueryService.getMemberList().stream()
                .map(MemberInfo.Main::new)
                .collect(Collectors.toList()); // TODO 이거 infrastructure 로 빼야함,,
    }

    @Override
    @Transactional(readOnly = true)
    public MemberInfo.Main retrieveMember(String memberToken) {
        var member = memberQueryService.getMember(memberToken);
        return new MemberInfo.Main(member);
    }

    @Override
    @Transactional(readOnly = true)
    public MemberInfo.Main retrieveLoginMember(String memberLoginId) {
        var member = memberQueryService.getLoginMember(memberLoginId);
        return new MemberInfo.Main(member);
    }

    @Override
    @Transactional
    public String registerMember(MemberCommand.RegisterMember registerMemberCommand) {
        String encodePassword = encodePassword(registerMemberCommand.getLoginPassword());
        registerMemberCommand.setLoginPassword(encodePassword);

        var member = registerMemberCommand.toEntity();
        var createdMember = memberCommandService.save(member);
        return createdMember.getToken();
    }

    @Override
    @Transactional
    public void changeMember(MemberCommand.ChangeMember changeMemberCommand, String memberToken) {
        var member = memberQueryService.getMember(memberToken);
        member.updateMember(
                changeMemberCommand.getLoginId(),
                changeMemberCommand.getName(),
                changeMemberCommand.getEmail(),
                changeMemberCommand.getAvatar(),
                changeMemberCommand.getNickName(),
                changeMemberCommand.getSelfIntroduction(),
                changeMemberCommand.getCategoryList(),
                changeMemberCommand.getCategoryItemList()
        );
    }

    @Override
    @Transactional
    public void changeMemberPassword(MemberCommand.ChangeMemberPassword changeMemberPasswordCommand, String memberToken) {
        var member = memberQueryService.getMember(memberToken);
        String encodePassword = encodePassword(changeMemberPasswordCommand.getLoginPassword());
        changeMemberPasswordCommand.setLoginPassword(encodePassword);
        member.updateMemberPassword(changeMemberPasswordCommand.getLoginPassword());
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

    @Override
    @Transactional
    public void deleteMember(String memberToken) {
        var member = memberQueryService.getMember(memberToken);
        memberCommandService.delete(member);
    }

    private String encodePassword(String password) {
        if (!ObjectUtils.isEmpty(password)) {
            return passwordEncoder.encode(password);
        }
        return null;
    }
}
