package com.sss.interfaces;

import com.sss.application.MemberFacade;
import com.sss.domain.Member;
import com.sss.domain.MemberInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class MemberApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberFacade memberFacade;

    @Test
    @DisplayName("1. 회원 목록 조회")
    void retrieveMembers() throws Exception {
        // given
        List<MemberInfo> response = Arrays.asList(
                MemberInfo.builder()
                        .id(1L)
                        .loginId("admin")
                        .loginPassword("1234")
                        .name("사랑의하츄핑")
                        .email("admin@email.com")
                        .status(Member.Status.ENABLE)
                        .build()
        );

        given(memberFacade.retrieveMembers()).willReturn(response);

        // when
        ResultActions resultActions = mockMvc.perform(get("/api/v1/members")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                ;

        // then
        resultActions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].memberId").exists())
                .andExpect(jsonPath("$[0].memberId").value(1))
                .andExpect(jsonPath("$[0].memberLoginId").value("admin"))
                .andExpect(jsonPath("$[0].memberLoginPassword").value("1234"))
                .andExpect(jsonPath("$[0].memberName").value("사랑의하츄핑"))
                .andExpect(jsonPath("$[0].memberEmail").value("admin@email.com"))
                .andExpect(jsonPath("$[0].memberStatus").value(Member.Status.ENABLE.name()))
                ;
    }
}