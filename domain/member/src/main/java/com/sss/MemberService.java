package com.sss;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface MemberService extends UserDetailsService {

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
    List<MemberInfo.Main> retrieveMembers();
    String registerMember(MemberCommand.RegisterMember registerMemberCommand);
    MemberInfo.Main retrieveMember(String memberToken);
    void changeMember(MemberCommand.ChangeMember changeMemberCommand, String memberToken);
    void enableMember(String memberToken);
    void disableMember(String memberToken);
    void deleteMember(String memberToken);
}
