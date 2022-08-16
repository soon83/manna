package com.sss.domain.member;

import com.sss.domain.category.CategoryQueryService;
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
    private final CategoryQueryService categoryQueryService;
    private final MemberCommandService memberCommandService;
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
    public MemberQuery.Main fetchMemberByEmail(String memberEmail) {
        var member = memberQueryService.readMemberByEmail(memberEmail);
        return new MemberQuery.Main(member);
    }

    @Override
    @Transactional
    public String registerMember(MemberCommand.CreateMember command) {
        command.setPassword(encodePassword(command.getPassword()));
        var member = command.toEntity();
        var createdMember = memberCommandService.create(member);

        var interestList = command.getInterestList();
        var categoryItemIdList = interestList.stream()
                .map(MemberCommand.CreateInterest::getCategoryItemId)
                .collect(Collectors.toList()); // TODO 추상화 하기
        var categoryItemList = categoryQueryService.readCategoryItemListById(categoryItemIdList);
        categoryItemList.forEach(categoryItem -> {
            memberCommandService.create(Interest.builder()
                    .categoryItem(categoryItem)
                    .member(createdMember)
                    .build());
        }); // TODO 추상화 하기

        return createdMember.getToken();
    }

    @Override
    @Transactional
    public void modifyMember(MemberCommand.UpdateMember command, String memberToken) {
        var member = memberQueryService.readMember(memberToken);
        command.updateMember(member);
    }

    @Override
    @Transactional
    public void modifyMemberPassword(MemberCommand.UpdateMemberPassword command, String memberToken) {
        var member = memberQueryService.readMember(memberToken);
        command.setPassword(encodePassword(command.getPassword()));
        member.updateMemberPassword(command.getPassword());
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
