package com.sss.domain.member;

import java.util.List;

public interface MemberService {
    List<MemberInfo.Main> fetchMemberList();
    MemberInfo.Main fetchMember(String memberToken);
    MemberInfo.Main fetchLoginMember(String memberLoginId);
    String createMember(MemberCommand.CreateMember createMemberCommand);
    void updateMember(MemberCommand.UpdateMember updateMemberCommand, String memberToken);
    void updateMemberPassword(MemberCommand.UpdateMemberPassword updateMemberPasswordCommand, String memberToken);
    void enableMember(String memberToken);
    void disableMember(String memberToken);
    void deleteMember(String memberToken);
}
