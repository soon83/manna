package com.sss.domain.member;

import java.util.List;

public interface MemberService {
    List<MemberQuery.Main> fetchMemberList();
    MemberQuery.Main fetchMember(String memberToken);
    MemberQuery.Main fetchLoginMember(String memberLoginId);
    String registerMember(MemberCommand.CreateMember command);
    void modifyMember(MemberCommand.UpdateMember command, String memberToken);
    void modifyMemberPassword(MemberCommand.UpdateMemberPassword command, String memberToken);
    void enableMember(String memberToken);
    void disableMember(String memberToken);
    void removeMember(String memberToken);
}
