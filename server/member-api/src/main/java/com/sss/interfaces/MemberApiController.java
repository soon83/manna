package com.sss.interfaces;

import com.sss.UriGenerator;
import com.sss.application.MemberFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
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
        var memberInfos = memberFacade.retrieveMembers();
        var response = memberInfos.stream()
                .map(MemberDto.MainResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<MemberDto.RegisterResponse> registerMembers(
            @RequestBody @Valid MemberDto.RegisterRequest request) throws URISyntaxException {
        var registerMemberCommand = request.toRegisterMemberCommand();
        var memberToken = memberFacade.registerMember(registerMemberCommand);
        var response = new MemberDto.RegisterResponse(memberToken);
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(UriGenerator.getLocation(response.getMemberToken()))
                .body(response);
    }
}
