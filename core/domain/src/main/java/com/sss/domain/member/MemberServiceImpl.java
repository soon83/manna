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
    public List<MemberQuery.Main> fetchMemberList() {
        return memberQueryService.readMemberList().stream()
                .map(MemberQuery.Main::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public MemberQuery.Main fetchMember(String memberToken) {
        var member = memberQueryService.readMember(memberToken);
        return new MemberQuery.Main(member);
    }

    @Override
    @Transactional(readOnly = true)
    public MemberQuery.Main fetchLoginMember(String memberLoginId) {
        var member = memberQueryService.readLoginMember(memberLoginId);
        return new MemberQuery.Main(member);
    }

    @Override
    @Transactional
    public String registerMember(MemberCommand.CreateMember createMemberCommand) {
        createMemberCommand.setLoginPassword(encodePassword(createMemberCommand.getLoginPassword()));
        var member = createMemberCommand.toEntity();
        var createdMember = memberCommandService.create(member);
        return createdMember.getToken();
    }

    @Override
    @Transactional
    public void modifyMember(MemberCommand.UpdateMember updateMemberCommand, String memberToken) {
        var member = memberQueryService.readMember(memberToken);
        member.updateMember(
                updateMemberCommand.getName(),
                updateMemberCommand.getEmail(),
                updateMemberCommand.getAvatar(),
                updateMemberCommand.getNickName(),
                updateMemberCommand.getSelfIntroduction(),
                updateMemberCommand.getCategoryList(),
                updateMemberCommand.getCategoryItemList()
        );
    }

    @Override
    @Transactional
    public void modifyMemberPassword(MemberCommand.UpdateMemberPassword updateMemberPasswordCommand, String memberToken) {
        var member = memberQueryService.readMember(memberToken);
        updateMemberPasswordCommand.setLoginPassword(encodePassword(updateMemberPasswordCommand.getLoginPassword()));
        member.updateMemberPassword(updateMemberPasswordCommand.getLoginPassword());
    }

    @Override
    @Transactional
    public void enableMember(String memberToken) {
        var member = memberQueryService.readMember(memberToken);
        member.enable();
    }

    @Override
    @Transactional
    public void disableMember(String memberToken) {
        var member = memberQueryService.readMember(memberToken);
        member.disable();
    }

    @Override
    @Transactional
    public void removeMember(String memberToken) {
        disableMember(memberToken);
    }

    private String encodePassword(String password) {
        if (!ObjectUtils.isEmpty(password)) {
            return passwordEncoder.encode(password);
        }
        return null;
    }
}
