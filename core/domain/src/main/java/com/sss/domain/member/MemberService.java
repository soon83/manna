package com.sss.domain.member;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberService {
    Page<MemberQuery.Main> fetchMemberList(MemberQuery.SearchConditionInfo condition, Pageable pageable);
    List<MemberQuery.WithInterestInfo> fetchMemberWithInterestList();
    MemberQuery.WithInterestInfo fetchMember(String memberToken);
    MemberQuery.Main fetchMemberByEmail(String memberEmail);
    String registerMember(MemberCommand.CreateMember command);
    void modifyMember(MemberCommand.UpdateMember command, String memberToken);
    void modifyMemberPassword(MemberCommand.UpdateMemberPassword command, String memberToken);
    void enableMember(String memberToken);
    void disableMember(String memberToken);
    void removeMember(String memberToken);
}
