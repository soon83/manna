package com.sss.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberQueryService memberQueryService;

    @Override
    @Transactional(readOnly = true)
    public List<MemberInfo> retrieveMembers() {
        return memberQueryService.getMembers();
    }
}
