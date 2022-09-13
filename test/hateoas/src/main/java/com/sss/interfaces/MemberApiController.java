package com.sss.interfaces;

import com.sss.application.MemberFacade;
import com.sss.domain.MemberCommand;
import com.sss.domain.MemberQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberApiController {

    private final MemberFacade memberFacade;
    private final MemberModelFetchAssembler fetchAssembler;
    private final MemberModelRegisterAssembler registerAssembler;
    private final PagedResourcesAssembler<MemberDto.MainResponse> pagedAssembler;

    /**
     * 회원 단건 등록
     */
    @PostMapping
    public ResponseEntity<MemberDto.RegisterResponse> registerMember(
            @RequestBody @Valid MemberDto.RegisterRequest request
    ) throws URISyntaxException {
        MemberCommand.Register registerCommand = request.toRegisterCommand();
        MemberQuery.Main member = memberFacade.registerMember(registerCommand);
        MemberDto.RegisterResponse response = registerAssembler.toModel(member);
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(new URI("/api/v1/member/" + member.getMemberId()))
                .body(response);
    }

    /**
     * 회원 목록 조회
     */
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<MemberDto.MainResponse>>> fetchMemberList(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<MemberQuery.Main> memberPageList = memberFacade.fetchMemberList(pageable);
        Page<MemberDto.MainResponse> memberPageResponseList = memberPageList.map(MemberDto.MainResponse::new);
        PagedModel<EntityModel<MemberDto.MainResponse>> response = pagedAssembler.toModel(memberPageResponseList, fetchAssembler);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * 회원 단건 조회
     */
    @GetMapping("/{memberId}")
    public ResponseEntity<EntityModel<MemberDto.MainResponse>> fetchMember(@PathVariable Long memberId) {
        MemberQuery.Main member = memberFacade.fetchMember(memberId);
        MemberDto.MainResponse memberResponse = new MemberDto.MainResponse(member);
        EntityModel<MemberDto.MainResponse> response = fetchAssembler.toModel(memberResponse);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
