package com.sss.domain.member;

import com.sss.domain.category.CategoryQueryService;
import com.sss.domain.category.item.CategoryItem;
import com.sss.domain.member.interest.Interest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final CategoryQueryService categoryQueryService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public Page<MemberQuery.Main> fetchMemberList(MemberQuery.SearchConditionInfo condition, Pageable pageable) {
        return memberQueryService.readMemberList(condition, pageable)
                .map(MemberQuery.Main::new);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MemberQuery.WithInterestInfo> fetchMemberWithInterestList() {
        var memberList = memberQueryService.fetchMemberWithInterestList();
        return memberQueryService.memberListMapper(memberList);
    }

    @Override
    @Transactional(readOnly = true)
    public MemberQuery.WithInterestInfo fetchMember(String memberToken) {
        var member = memberQueryService.readMember(memberToken);
        var interestList = member.getInterestList();
        var interestInfoList = memberQueryService.memberInterestListMapper(interestList);
        return new MemberQuery.WithInterestInfo(member, interestInfoList);
    }

    @Override
    @Transactional(readOnly = true)
    public MemberQuery.Main fetchMemberByLoginId(String memberLoginId) {
        var member = memberQueryService.readMemberByLoginId(memberLoginId);
        return new MemberQuery.Main(member);
    }

    @Override
    @Transactional
    public String registerMember(MemberCommand.CreateMember command) {
        command.setLoginPassword(encodePassword(command.getLoginPassword()));
        /*var interestList = command.getInterestList();
        var categoryItemIdList = interestList.stream()
                .map(MemberCommand.CreateInterest::getCategoryItemId)
                .collect(Collectors.toList());// TODO 추상화 하기
        var categoryItemList = categoryQueryService.readCategoryItemListById(categoryItemIdList);*/
        var member = command.toEntity();
        var createdMember = memberCommandService.create(member);
        return createdMember.getToken();
    }

    @Override
    @Transactional
    public void modifyMember(MemberCommand.UpdateMember command, String memberToken) {
        var member = memberQueryService.readMember(memberToken);
        member.updateMember(
                command.getName(),
                command.getEmail(),
                command.getAvatar(),
                command.getNickName(),
                command.getSelfIntroduction()
        );
    }

    @Override
    @Transactional
    public void modifyMemberPassword(MemberCommand.UpdateMemberPassword command, String memberToken) {
        var member = memberQueryService.readMember(memberToken);
        command.setLoginPassword(encodePassword(command.getLoginPassword()));
        member.updateMemberPassword(command.getLoginPassword());
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
