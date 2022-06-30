package com.sss.interfaces;

import com.sss.application.MemberFacade;
import com.sss.domain.MemberInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberFacade memberFacade;

    /**
     * 회원 목록 조회
     * @return
     */
    @GetMapping
    public ResponseEntity<List<MemberDto.MainResponse>> retrieveMembers() {
        var memberInfoList = memberFacade.retrieveMembers();
        var response = memberInfoList.stream()
                .map(MemberDto.MainResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<Long> registerMembers(@RequestBody @Valid MemberDto.RegisterRequest request) {
        return 1L;
    }
}
