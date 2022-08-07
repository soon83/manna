package com.sss.login;

import com.sss.member.MemberDto;
import com.sss.member.MemberFacade;
import com.sss.response.Res;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/login")
public class LoginApiController {
    private final MemberFacade memberFacade;

    @GetMapping("/find-member-email")
    public ResponseEntity<Res> findMemberLoginId(@ModelAttribute @Valid LoginDto.CheckEmailRequest request) {
        String memberEmail = request.getMemberEmail();
        var memberByEmail = memberFacade.fetchMemberByEmail(memberEmail);
        var response = new MemberDto.MainResponse(memberByEmail);
        return ResponseEntity.status(HttpStatus.OK).body(Res.success(response));
    }
}
