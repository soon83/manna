package com.sss.domain;

import java.util.List;

public interface MemberService {

    List<MemberInfo.Main> retrieveMembers();

    String registerMember(MemberCommand.RegisterMember registerMemberCommand);
}
