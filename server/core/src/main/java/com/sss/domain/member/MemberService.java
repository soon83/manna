package com.sss.domain.member;

import java.util.List;

public interface MemberService {

    List<MemberInfo.Main> retrieveMembers();
    MemberInfo.Main retrieveMember(String memberToken);
    MemberInfo.Main retrieveLoginMember(String memberLoginId);
    String registerMember(MemberCommand.RegisterMember registerMemberCommand);
    void changeMember(MemberCommand.ChangeMember changeMemberCommand, String memberToken);
    void changeMemberPassword(MemberCommand.ChangeMemberPassword changeMemberPasswordCommand, String memberToken);
    void enableMember(String memberToken);
    void disableMember(String memberToken);
    void deleteMember(String memberToken);
}
