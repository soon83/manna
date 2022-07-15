package com.sss.config;

import com.sss.domain.member.MemberCommand;
import com.sss.member.MemberFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class Init implements InitializingBean {

    private final MemberFacade memberFacade;

    @Override
    public void afterPropertiesSet() throws Exception {
        // 관리자가 없으면 생성한다.
        try {
            memberFacade.retrieveLoginMember("admin");
        } catch (Exception e) {
            memberFacade.registerMember(MemberCommand.RegisterMember.builder()
                    .loginId("admin")
                    .loginPassword(new BCryptPasswordEncoder().encode("1234"))
                    .name("사랑의하츄핑")
                    .email("admin@email.com")
                    .build());
        }
    }
}