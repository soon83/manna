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
        var memberInfoList = memberFacade.retrieveMembers();
        var response = memberInfoList.stream()
                .map(MemberDto.MainResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * 회원 단건 등록
     * @param request
     * @return
     * @throws URISyntaxException
     */
    @PostMapping
    public ResponseEntity<MemberDto.RegisterResponse> registerMembers(
            @RequestBody @Valid MemberDto.RegisterRequest request
    ) throws URISyntaxException {
        var registerMemberCommand = request.toRegisterMemberCommand();
        var memberToken = memberFacade.registerMember(registerMemberCommand);
        var response = new MemberDto.RegisterResponse(memberToken);
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(UriGenerator.getLocation(response.getMemberToken()))
                .body(response);
    }

    /**
     * 회원 단건 조회
     * @param memberToken
     * @return
     */
    @GetMapping("/{memberToken}")
    public ResponseEntity<MemberDto.MainResponse> retrieveMember(@PathVariable String memberToken) {
        var memberInfo = memberFacade.retrieveMember(memberToken);
        var response = new MemberDto.MainResponse(memberInfo);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{memberToken}")
    public ResponseEntity<MemberDto.ChangeRequest> changeMember(
            @PathVariable String memberToken,
            @RequestBody @Valid MemberDto.ChangeRequest request
    ) {
        var changeMemberCommand = request.toChangeMemberCommand();
        memberFacade.changeMember(changeMemberCommand, memberToken);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
