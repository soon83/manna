package com.sss.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sss.domain.member.Member;
import com.sss.domain.member.MemberCommand;
import com.sss.domain.member.MemberInfo;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static com.sss.restdocs.ApiDocumentUtils.descriptionsForNameProperty;
import static com.sss.restdocs.ApiDocumentUtils.uriModifyingOperationPreprocessor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberApiController.class)
@ExtendWith(RestDocumentationExtension.class)
class UnitCreateMemberApiDocumentationTest {

    @MockBean
    private MemberFacade memberFacade;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private MockMvc mockMvc;

    @BeforeEach
    public void setUpRestdocs(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation)
                        .operationPreprocessors()
                        .withRequestDefaults(uriModifyingOperationPreprocessor(), prettyPrint())
                        .withResponseDefaults(prettyPrint()))
                .build();
    }

    @Test
    @DisplayName("[회원] 목록 조회")
    void fetchMemberList() throws Exception {
        // given
        List<MemberInfo.Main> memberInfoList = Lists.newArrayList(
                new MemberInfo.Main(
                        "mbr_Bk3GlYuJSFNWqkHw",
                        "tester",
                        "1234",
                        "테스터",
                        "tester@email.com",
                        "/tester/avatar/path",
                        "하츄핑",
                        "저는 테스터입니다.",
                        "[1, 2, 3, 4]",
                        "[5, 6, 7, 8]",
                        Member.Role.MEMBER,
                        Member.Status.ENABLE
                )
        );

        // when
        when(memberFacade.fetchMemberList()).thenReturn(memberInfoList);

        // then
        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/member-list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("fetch-member-list",
                        responseFields(
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("결과"),
                                fieldWithPath("result").type(JsonFieldType.STRING).description("결과코드"),
                                fieldWithPath("data.[0].memberToken").type(JsonFieldType.STRING).description("회원 토큰"),
                                fieldWithPath("data.[0].memberLoginId").type(JsonFieldType.STRING).description("회원 로그인 아이디"),
                                fieldWithPath("data.[0].memberName").type(JsonFieldType.STRING).description("회원 이름"),
                                fieldWithPath("data.[0].memberEmail").type(JsonFieldType.STRING).description("회원 이메일"),
                                fieldWithPath("data.[0].memberAvatar").type(JsonFieldType.STRING).description("회원 이미지"),
                                fieldWithPath("data.[0].memberNickName").type(JsonFieldType.STRING).description("회원 별명"),
                                fieldWithPath("data.[0].memberSelfIntroduction").type(JsonFieldType.STRING).description("회원 자기소개"),
                                fieldWithPath("data.[0].memberCategoryList").type(JsonFieldType.STRING).description("회원 관심사 - 대분류"),
                                fieldWithPath("data.[0].memberCategoryItemList").type(JsonFieldType.STRING).description("회원 관심사 - 소분류"),
                                fieldWithPath("data.[0].memberRole").type(JsonFieldType.STRING).description("회원 권한 (MANAGER / MEMBER)"),
                                fieldWithPath("data.[0].memberStatus").type(JsonFieldType.STRING).description("회원 활성화 상태 (ENABLE / DISABLE)")
                        )
                ));
    }

    @Test
    @DisplayName("[회원] 단건 조회 - 토큰으로")
    void fetchMemberByToken() throws Exception {
        // given
        String memberToken = "mbr_Bk3GlYuJSFNWqkHw";
        MemberInfo.Main memberInfo = new MemberInfo.Main(
                "mbr_Bk3GlYuJSFNWqkHw",
                "tester",
                "1234",
                "테스터",
                "tester@email.com",
                "/tester/avatar/path",
                "하츄핑",
                "저는 테스터입니다.",
                "[1, 2, 3, 4]",
                "[5, 6, 7, 8]",
                Member.Role.MEMBER,
                Member.Status.ENABLE
        );

        // when
        when(memberFacade.fetchMember(eq(memberToken))).thenReturn(memberInfo);

        // then
        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/member-list/{memberToken}", memberToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("fetch-member-by-token",
                        pathParameters(
                                parameterWithName("memberToken").description("회원 토큰")
                        ),
                        responseFields(
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("결과"),
                                fieldWithPath("result").type(JsonFieldType.STRING).description("결과코드"),
                                fieldWithPath("data.memberToken").type(JsonFieldType.STRING).description("회원 토큰"),
                                fieldWithPath("data.memberLoginId").type(JsonFieldType.STRING).description("회원 로그인 아이디"),
                                fieldWithPath("data.memberName").type(JsonFieldType.STRING).description("회원 이름"),
                                fieldWithPath("data.memberEmail").type(JsonFieldType.STRING).description("회원 이메일"),
                                fieldWithPath("data.memberAvatar").type(JsonFieldType.STRING).description("회원 이미지"),
                                fieldWithPath("data.memberNickName").type(JsonFieldType.STRING).description("회원 별명"),
                                fieldWithPath("data.memberSelfIntroduction").type(JsonFieldType.STRING).description("회원 자기소개"),
                                fieldWithPath("data.memberCategoryList").type(JsonFieldType.STRING).description("회원 관심사 - 대분류"),
                                fieldWithPath("data.memberCategoryItemList").type(JsonFieldType.STRING).description("회원 관심사 - 소분류"),
                                fieldWithPath("data.memberRole").type(JsonFieldType.STRING).description("회원 권한 (MANAGER / MEMBER)"),
                                fieldWithPath("data.memberStatus").type(JsonFieldType.STRING).description("회원 활성화 상태 (ENABLE / DISABLE)")
                        )
                ));
    }

    @Test
    @DisplayName("[회원] 단건 등록")
    void createMember() throws Exception {
        // given
        String memberToken = "mbr_Bk3GlYuJSFNWqkHw";
        MemberDto.CreateRequest memberCreateRequest = MemberDto.CreateRequest.builder()
                .memberLoginId("tester")
                .memberLoginPassword("1234")
                .memberName("테스터")
                .memberEmail("tester@email.com")
                .memberAvatar("/tester/avatar/path")
                .memberNickName("하츄핑")
                .memberSelfIntroduction("저는 테스터입니다.")
                .memberCategoryList(Arrays.asList(1,2,3,4))
                .memberCategoryItemList(Arrays.asList(5,6,7,8))
                .build();

        // when
        when(memberFacade.createMember(any(MemberCommand.CreateMember.class))).thenReturn(memberToken);

        // then
        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/api/v1/member-list")
                        .content(objectMapper.writeValueAsString(memberCreateRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("create-member",
                        requestFields(
                                fieldWithPath("memberLoginId").type(JsonFieldType.STRING).description("회원 로그인 아이디")
                                        .attributes(key("constraints").value(descriptionsForNameProperty(MemberDto.CreateRequest.class, "memberLoginId"))),
                                fieldWithPath("memberLoginPassword").type(JsonFieldType.STRING).description("회원 로그인 비밀번호")
                                        .attributes(key("constraints").value(descriptionsForNameProperty(MemberDto.CreateRequest.class, "memberLoginPassword"))),
                                fieldWithPath("memberName").type(JsonFieldType.STRING).description("회원 이름")
                                        .attributes(key("constraints").value(descriptionsForNameProperty(MemberDto.CreateRequest.class, "memberName"))),
                                fieldWithPath("memberEmail").type(JsonFieldType.STRING).description("회원 이메일")
                                        .attributes(key("constraints").value(descriptionsForNameProperty(MemberDto.CreateRequest.class, "memberEmail"))),
                                fieldWithPath("memberAvatar").type(JsonFieldType.STRING).description("회원 이미지").optional()
                                        .attributes(key("constraints").value(descriptionsForNameProperty(MemberDto.CreateRequest.class, "memberAvatar"))),
                                fieldWithPath("memberNickName").type(JsonFieldType.STRING).description("회원 별명")
                                        .attributes(key("constraints").value(descriptionsForNameProperty(MemberDto.CreateRequest.class, "memberNickName"))),
                                fieldWithPath("memberSelfIntroduction").type(JsonFieldType.STRING).description("회원 자기소개")
                                        .attributes(key("constraints").value(descriptionsForNameProperty(MemberDto.CreateRequest.class, "memberSelfIntroduction"))),
                                fieldWithPath("memberCategoryList").type(JsonFieldType.ARRAY).description("회원 관심사 - 대분류")
                                        .attributes(key("constraints").value(descriptionsForNameProperty(MemberDto.CreateRequest.class, "memberCategoryList"))),
                                fieldWithPath("memberCategoryItemList").type(JsonFieldType.ARRAY).description("회원 관심사 - 소분류")
                                        .attributes(key("constraints").value(descriptionsForNameProperty(MemberDto.CreateRequest.class, "memberCategoryItemList")))
                        ),
                        responseFields(
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("결과"),
                                fieldWithPath("result").type(JsonFieldType.STRING).description("결과코드"),
                                fieldWithPath("data.memberToken").type(JsonFieldType.STRING).description("회원 토큰")
                        )
                ));
    }
}
