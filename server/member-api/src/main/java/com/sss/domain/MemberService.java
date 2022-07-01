package com.sss.domain;

import java.util.List;

public interface MemberService {

    List<MemberInfo.Main> retrieveMembers();

    MemberInfo.Main retrieveMember(String memberToken);

    String registerMember(MemberCommand.RegisterMember registerMemberCommand);

    void changeMember(MemberCommand.ChangeMember changeMemberCommand, String memberToken);

    void enableMember(String memberToken);

    void disableMember(String memberToken);
}
