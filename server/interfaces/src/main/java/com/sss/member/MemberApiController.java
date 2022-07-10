package com.sss.member;

import com.sss.MemberFacade;
import com.sss.common.response.Res;
import com.sss.util.UriGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.stream.Collectors;

@Slf4j
@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberFacade memberFacade;

    /**
     * 회원 목록 조회
     * @return
     */
    @GetMapping
    public ResponseEntity<Res> retrieveMembers() {
        var memberInfoList = memberFacade.retrieveMembers();
        var response = memberInfoList.stream()
                .map(MemberDto.MainResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(Res.success(response));
    }

    /**
     * 회원 단건 등록
     * @param request
     * @return
     * @throws URISyntaxException
     */
    @PostMapping
    public ResponseEntity<Res> registerMembers(@RequestBody @Valid MemberDto.RegisterRequest request) throws URISyntaxException {
        var registerMemberCommand = request.toRegisterMemberCommand();
        var memberToken = memberFacade.registerMember(registerMemberCommand);
        var response = new MemberDto.RegisterResponse(memberToken);
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(UriGenerator.getLocation(response.getMemberToken()))
                .body(Res.success(response));
    }

    /**
     * 회원 단건 조회
     * @param memberToken
     * @return
     */
    @GetMapping("/{memberToken}")
    public ResponseEntity<Res> retrieveMember(@PathVariable String memberToken) {
        var memberInfo = memberFacade.retrieveMember(memberToken);
        var response = new MemberDto.MainResponse(memberInfo);
        return ResponseEntity.status(HttpStatus.OK).body(Res.success(response));
    }

    /**
     * 회원 단건 수정
     * @param memberToken
     * @param request
     * @return
     */
    @PutMapping("/{memberToken}")
    public ResponseEntity<Res> changeMember(@PathVariable String memberToken, @RequestBody @Valid MemberDto.ChangeRequest request) {
        var changeMemberCommand = request.toChangeMemberCommand();
        memberFacade.changeMember(changeMemberCommand, memberToken);
        return ResponseEntity.status(HttpStatus.OK).body(Res.success());
    }

    /**
     * 회원 단건 수정 - 회원 비활성화
     * @param request
     * @return
     */
    @PatchMapping("/disable")
    public ResponseEntity<Res> disableMember(@RequestBody @Valid MemberDto.ChangeMemberStatusRequest request) {
        var memberToken = request.getMemberToken();
        memberFacade.disableMember(memberToken);
        return ResponseEntity.status(HttpStatus.OK).body(Res.success());
    }

    /**
     * 회원 단건 수정 - 회원 활성화
     * @param request
     * @return
     */
    @PatchMapping("/enable")
    public ResponseEntity<Res> enableMember(@RequestBody @Valid MemberDto.ChangeMemberStatusRequest request) {
        var memberToken = request.getMemberToken();
        memberFacade.enableMember(memberToken);
        return ResponseEntity.status(HttpStatus.OK).body(Res.success());
    }

    /**
     * 회원 단건 삭제
     * @param memberToken
     * @return
     */
    @DeleteMapping("/{memberToken}")
    public ResponseEntity<Res> deleteMember(@PathVariable String memberToken) {
        memberFacade.deleteMember(memberToken);
        return ResponseEntity.status(HttpStatus.OK).body(Res.success());
    }
}
