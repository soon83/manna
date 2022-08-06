package com.sss.config;

import com.sss.domain.meeting.Meeting;
import com.sss.domain.member.Member;
import com.sss.enumcode.EnumMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnumConfig {

    @Bean
    public EnumMapper enumMapper() {
        EnumMapper enumMapper = new EnumMapper();
        enumMapper.put("member-role", Member.Role.class);
        enumMapper.put("member-status", Member.Status.class);
        enumMapper.put("meeting-recruitment-method", Meeting.RecruitmentMethod.class);
        return enumMapper;
    }
}
