package com.sss.config;

import com.sss.domain.member.MemberCommand;
import com.sss.member.MemberFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.List;

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
                            .loginPassword("1234")
                            .name("하츄핑")
                            .email("admin@email.com")
                            .avatar("/avatar/file/path")
                            .nickName("사랑의하츄핑")
                            .selfIntroduction("나는 하츄핑이야 츄")
                            .categories(List.of(1,2,3,4))
                            .categoryItems(List.of(5,6,7,8))
                    .build());
        }
    }
}
