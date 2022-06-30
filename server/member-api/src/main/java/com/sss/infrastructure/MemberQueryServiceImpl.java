package com.sss.infrastructure;

import com.sss.domain.Member;
import com.sss.domain.MemberInfo;
import com.sss.domain.MemberQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberQueryServiceImpl implements MemberQueryService {

    private final MemberRepository memberRepository;

    @Override
    public List<MemberInfo> getMembers() {
        List<Member> memberList = memberRepository.findAll();
        return memberList.stream()
                .map(MemberInfo::new)
                .collect(Collectors.toList());
    }
}
