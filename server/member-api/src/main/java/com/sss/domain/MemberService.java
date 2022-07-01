package com.sss.domain;

import java.util.List;

public interface MemberService {

    List<MemberInfo.Main> retrieveMembers();
    String registerMember(MemberCommand.RegisterMember registerMemberCommand);
    MemberInfo.Main retrieveMember(String memberToken);
    void changeMember(MemberCommand.ChangeMember changeMemberCommand, String memberToken);
    void enableMember(String memberToken);
    void disableMember(String memberToken);
    void deleteMember(String memberToken);
}
