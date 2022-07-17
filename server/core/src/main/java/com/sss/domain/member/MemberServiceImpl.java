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
    public List<MemberInfo.Main> fetchMemberList() {
        return memberQueryService.getMemberList().stream()
                .map(MemberInfo.Main::new)
                .collect(Collectors.toList()); // TODO 이거 infrastructure 로 빼야함,, 구현코드는 모두 추상화하자
    }

    @Override
    @Transactional(readOnly = true)
    public MemberInfo.Main fetchMember(String memberToken) {
        var member = memberQueryService.getMember(memberToken);
        return new MemberInfo.Main(member);
    }

    @Override
    @Transactional(readOnly = true)
    public MemberInfo.Main fetchLoginMember(String memberLoginId) {
        var member = memberQueryService.getLoginMember(memberLoginId);
        return new MemberInfo.Main(member);
    }

    @Override
    @Transactional
    public String createMember(MemberCommand.CreateMember createMemberCommand) {
        String encodePassword = encodePassword(createMemberCommand.getLoginPassword());
        createMemberCommand.setLoginPassword(encodePassword);

        var member = createMemberCommand.toEntity();
        var createdMember = memberCommandService.save(member);
        return createdMember.getToken();
    }

    @Override
    @Transactional
    public void updateMember(MemberCommand.UpdateMember updateMemberCommand, String memberToken) {
        var member = memberQueryService.getMember(memberToken);
        member.updateMember(
                updateMemberCommand.getLoginId(),
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
    public void updateMemberPassword(MemberCommand.UpdateMemberPassword updateMemberPasswordCommand, String memberToken) {
        var member = memberQueryService.getMember(memberToken);
        String encodePassword = encodePassword(updateMemberPasswordCommand.getLoginPassword());
        updateMemberPasswordCommand.setLoginPassword(encodePassword);
        member.updateMemberPassword(updateMemberPasswordCommand.getLoginPassword());
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
        disableMember(memberToken);
    }

    private String encodePassword(String password) {
        if (!ObjectUtils.isEmpty(password)) {
            return passwordEncoder.encode(password);
        }
        return null;
    }
}
