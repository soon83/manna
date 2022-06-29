package com.sss.interfaces;

import com.sss.application.MemberFacade;
import com.sss.domain.MemberInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberFacade memberFacade;

    public ResponseEntity<List<MemberDto.GetResponse>> retrieveMembers() {
        List<MemberInfo> memberInfoList = memberFacade.retrieveMembers();
        List<MemberDto.GetResponse> responseList = memberInfoList.stream()
                .map(MemberDto.GetResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(responseList);
    }
}
