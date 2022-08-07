package com.sss.domain.login;

import com.sss.domain.member.MemberQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final MemberQueryService memberQueryService;

    @Override
    @Transactional(readOnly = true)
    public LoginInfo.AccountAdaptor loadUserByUsername(String memberEmail) throws UsernameNotFoundException {
        var member = memberQueryService.readMemberByEmail(memberEmail);
        var loginInfo = new LoginInfo.Main(member);
        return new LoginInfo.AccountAdaptor(loginInfo);
    }
}
