package com.sss.domain;

import com.sss.infrastructure.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberQueryService {

    private final MemberRepository memberRepository;

    public Member fetchMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("그런 Member 또 없습니다. -이승철-"));
    }

    public Page<Member> fetchMemberList(Pageable pageable) {
        return memberRepository.findAll(pageable);
    }
}
