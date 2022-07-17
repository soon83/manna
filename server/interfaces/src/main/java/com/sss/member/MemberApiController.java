package com.sss.member;

import com.sss.response.Res;
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
@RequestMapping("/api/v1/member-list")
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberFacade memberFacade;

    /**
     * 회원 목록 조회
     * @return
     */
    @GetMapping
    public ResponseEntity<Res> fetchMemberList() {
        var memberInfoList = memberFacade.fetchMemberList();
        var response = memberInfoList.stream()
                .map(MemberDto.MainResponse::new)
                .collect(Collectors.toList()); // TODO 이거 infrastructure 로 빼야함,, 구현코드는 모두 추상화하자
        return ResponseEntity.status(HttpStatus.OK).body(Res.success(response));
    }

    /**
     * 회원 단건 조회
     * @param memberToken
     * @return
     */
    @GetMapping("/{memberToken}")
    public ResponseEntity<Res> fetchMember(@PathVariable String memberToken) {
        var memberInfo = memberFacade.fetchMember(memberToken);
        var response = new MemberDto.MainResponse(memberInfo);
        return ResponseEntity.status(HttpStatus.OK).body(Res.success(response));
    }

    /**
     * 회원 단건 등록
     * @param request
     * @return
     * @throws URISyntaxException
     */
    @PostMapping
    public ResponseEntity<Res> createMember(@RequestBody @Valid MemberDto.CreateRequest request) throws URISyntaxException {
        var createMemberCommand = request.toCreateMemberCommand();
        var memberToken = memberFacade.createMember(createMemberCommand);
        var response = new MemberDto.CreateResponse(memberToken);
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(UriGenerator.getLocation(response.getMemberToken()))
                .body(Res.success(response));
    }

    /**
     * 회원 단건 수정
     * @param memberToken
     * @param request
     * @return
     */
    @PutMapping("/{memberToken}")
    public ResponseEntity<Res> updateMember(@PathVariable String memberToken, @RequestBody @Valid MemberDto.UpdateRequest request) {
        var updateMemberCommand = request.toUpdateMemberCommand();
        memberFacade.updateMember(updateMemberCommand, memberToken);
        return ResponseEntity.status(HttpStatus.OK).body(Res.success());
    }

    /**
     * 회원 단건 수정 - 회원 비밀번호
     * @param request
     * @return
     */
    @PatchMapping("/password")
    public ResponseEntity<Res> updateMemberPassword(@RequestBody @Valid MemberDto.UpdateMemberPasswordRequest request) {
        var memberToken = request.getMemberToken();
        var updateMemberPasswordCommand = request.toUpdateMemberPasswordCommand();
        memberFacade.updateMemberPassword(updateMemberPasswordCommand, memberToken);
        return ResponseEntity.status(HttpStatus.OK).body(Res.success());
    }

    /**
     * 회원 단건 수정 - 회원 비활성화
     * @param request
     * @return
     */
    @PatchMapping("/disable")
    public ResponseEntity<Res> disableMember(@RequestBody @Valid MemberDto.UpdateMemberStatusRequest request) {
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
    public ResponseEntity<Res> enableMember(@RequestBody @Valid MemberDto.UpdateMemberStatusRequest request) {
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
