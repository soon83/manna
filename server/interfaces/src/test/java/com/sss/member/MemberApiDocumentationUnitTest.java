package com.sss.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sss.category.CategoryDto;
import com.sss.domain.category.CategoryQuery;
import com.sss.domain.login.LoginInfo;
import com.sss.domain.member.Member;
import com.sss.domain.member.MemberCommand;
import com.sss.domain.member.MemberQuery;
import com.sss.jwt.JwtUtil;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static com.sss.restdocs.ApiDocumentUtils.descriptionsForNameProperty;
import static com.sss.restdocs.ApiDocumentUtils.uriModifyingOperationPreprocessor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("local")
@WebMvcTest(MemberApiController.class)
@ExtendWith(RestDocumentationExtension.class)
class MemberApiDocumentationUnitTest {
    private MockMvc mockMvc;
    @MockBean
    private MemberFacade memberFacade;
    @MockBean
    private MemberMapper memberMapper;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private static String AUTH_TOKEN;
    private static String MEMBER_TOKEN = "mbr_Bk3GlYuJSFNWqkHw";

    @BeforeAll
    public static void login() {
        String token = JwtUtil.makeAuthToken(LoginInfo.Main.builder()
                .memberEmail("admin@email.com")
                .memberPassword(passwordEncoder.encode("1234"))
                .build());
        AUTH_TOKEN = "Bearer " + token;
    }

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
        List<MemberQuery.Main> response = Lists.newArrayList(
                MemberQuery.Main.builder()
                        .token(MEMBER_TOKEN)
                        .email("tester@email.com")
                        .password("1234")
                        .name("테스터")
                        .avatar("/tester/avatar/path")
                        .nickName("하츄핑")
                        .selfIntroduction("저는 테스터입니다.")
                        .role(Member.Role.MEMBER)
                        .status(Member.Status.ENABLE)
                        .build()
        );
        Pageable pageable = PageRequest.of(0, 10, Sort.Direction.DESC, "id");
        Page<MemberQuery.Main> pageResponse = new PageImpl<>(response, pageable, 1);

        // when
        when(memberFacade.fetchMemberList(any(MemberQuery.SearchConditionInfo.class), any(Pageable.class))).thenReturn(pageResponse);
        when(memberMapper.memberInfoListMapper(pageResponse)).thenReturn(pageResponse.map(MemberDto.MainResponse::new));

        // then
        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/member")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", AUTH_TOKEN))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("fetch-member-list",
                        responseFields(
                                // common
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("결과"),
                                fieldWithPath("result").type(JsonFieldType.STRING).description("결과코드"),

                                // data.content[]
                                fieldWithPath("data.content[].memberToken").type(JsonFieldType.STRING).description("회원 토큰"),
                                fieldWithPath("data.content[].memberEmail").type(JsonFieldType.STRING).description("회원 이메일"),
                                fieldWithPath("data.content[].memberName").type(JsonFieldType.STRING).description("회원 이름"),
                                fieldWithPath("data.content[].memberAvatar").type(JsonFieldType.STRING).description("회원 이미지"),
                                fieldWithPath("data.content[].memberNickName").type(JsonFieldType.STRING).description("회원 별명"),
                                fieldWithPath("data.content[].memberSelfIntroduction").type(JsonFieldType.STRING).description("회원 자기소개"),
                                fieldWithPath("data.content[].memberRole").type(JsonFieldType.STRING).description("회원 권한 [MANAGER, MEMBER]"),
                                fieldWithPath("data.content[].memberStatus").type(JsonFieldType.STRING).description("회원 활성화 상태 [ENABLE, DISABLE]"),

                                // data.pageable
                                fieldWithPath("data.pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("is sorted"),
                                fieldWithPath("data.pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("is unsorted"),
                                fieldWithPath("data.pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("is empty"),
                                fieldWithPath("data.pageable.pageNumber").type(JsonFieldType.NUMBER).description("pageNumber"),
                                fieldWithPath("data.pageable.pageSize").type(JsonFieldType.NUMBER).description("pageSize"),
                                fieldWithPath("data.pageable.offset").type(JsonFieldType.NUMBER).description("offset"),
                                fieldWithPath("data.pageable.paged").type(JsonFieldType.BOOLEAN).description("paged"),
                                fieldWithPath("data.pageable.unpaged").type(JsonFieldType.BOOLEAN).description("unpaged"),
                                fieldWithPath("data.sort.sorted").type(JsonFieldType.BOOLEAN).description("is sorted"),
                                fieldWithPath("data.sort.unsorted").type(JsonFieldType.BOOLEAN).description("is unsorted"),
                                fieldWithPath("data.sort.empty").type(JsonFieldType.BOOLEAN).description("is empty"),
                                fieldWithPath("data.totalPages").type(JsonFieldType.NUMBER).description("totalPages"),
                                fieldWithPath("data.totalElements").type(JsonFieldType.NUMBER).description("totalElements"),
                                fieldWithPath("data.last").type(JsonFieldType.BOOLEAN).description("last"),
                                fieldWithPath("data.numberOfElements").type(JsonFieldType.NUMBER).description("numberOfElements"),
                                fieldWithPath("data.first").type(JsonFieldType.BOOLEAN).description("is first"),
                                fieldWithPath("data.size").type(JsonFieldType.NUMBER).description("size"),
                                fieldWithPath("data.number").type(JsonFieldType.NUMBER).description("number"),
                                fieldWithPath("data.empty").type(JsonFieldType.BOOLEAN).description("empty")
                        )
                ));
    }

    @Test
    @DisplayName("[회원] 단건 조회")
    void fetchMemberByMemberToken() throws Exception {
        // given
        List<MemberQuery.InterestInfo> interestInfoList = Lists.newArrayList(
                MemberQuery.InterestInfo.builder()
                        .token("itrt_eaea1DrYvJw1KHJ")
                        .categoryItemInfo(CategoryQuery.CategoryItemInfo.builder()
                                .token("ctgItm_TDL1vTlJ1mSIb")
                                .ordering(0)
                                .categoryToken("ctgItm_TDL1vTlJ1mSIb")
                                .build())
                        .build()
        );
        List<MemberDto.InterestResponse> response = Lists.newArrayList(
                MemberDto.InterestResponse.builder()
                        .interestToken("itrt_eaea1DrYvJw1KHJ")
                        .categoryItem(CategoryDto.CategoryItemResponse.builder()
                                .categoryItemOrdering(0)
                                .categoryItemToken("ctgItm_TDL1vTlJ1mSIb")
                                .categoryItemTitle("전시")
                                .categoryToken("ctgItm_TDL1vTlJ1mSIb")
                                .build())
                        .build()
        );
        MemberQuery.WithInterestInfo info = MemberQuery.WithInterestInfo.builder()
                .token(MEMBER_TOKEN)
                .email("tester@email.com")
                .password("1234")
                .name("테스터")
                .avatar("/tester/avatar/path")
                .nickName("하츄핑")
                .selfIntroduction("저는 테스터입니다.")
                .interestInfoList(interestInfoList)
                .role(Member.Role.MEMBER)
                .status(Member.Status.ENABLE)
                .build();

        // when
        when(memberFacade.fetchMember(any(String.class))).thenReturn(info);
        when(memberMapper.memberInterestInfoListMapper(interestInfoList)).thenReturn(response);

        // then
        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/member/{memberToken}", MEMBER_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", AUTH_TOKEN))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("fetch-member",
                        pathParameters(
                                parameterWithName("memberToken").description("회원 토큰")
                        ),
                        responseFields(
                                // common
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("결과"),
                                fieldWithPath("result").type(JsonFieldType.STRING).description("결과코드"),

                                // data
                                fieldWithPath("data.memberToken").type(JsonFieldType.STRING).description("회원 토큰"),
                                fieldWithPath("data.memberEmail").type(JsonFieldType.STRING).description("회원 이메일"),
                                fieldWithPath("data.memberName").type(JsonFieldType.STRING).description("회원 이름"),
                                fieldWithPath("data.memberAvatar").type(JsonFieldType.STRING).description("회원 이미지"),
                                fieldWithPath("data.memberNickName").type(JsonFieldType.STRING).description("회원 별명"),
                                fieldWithPath("data.memberSelfIntroduction").type(JsonFieldType.STRING).description("회원 자기소개"),
                                fieldWithPath("data.memberInterestList[].interestToken").type(JsonFieldType.STRING).description("interestToken"),
                                fieldWithPath("data.memberInterestList[].categoryItem.categoryItemToken").type(JsonFieldType.STRING).description("categoryItemToken"),
                                fieldWithPath("data.memberInterestList[].categoryItem.categoryItemTitle").type(JsonFieldType.STRING).description("categoryItemTitle"),
                                fieldWithPath("data.memberInterestList[].categoryItem.categoryItemOrdering").type(JsonFieldType.NUMBER).description("categoryItemOrdering"),
                                fieldWithPath("data.memberInterestList[].categoryItem.categoryToken").type(JsonFieldType.STRING).description("categoryToken"),
                                fieldWithPath("data.memberRole").type(JsonFieldType.STRING).description("회원 권한 [MANAGER, MEMBER]"),
                                fieldWithPath("data.memberStatus").type(JsonFieldType.STRING).description("회원 활성화 상태 [ENABLE, DISABLE]")
                        )
                ));
    }

    @Disabled
    @Test
    @DisplayName("[회원] 단건 등록")
    void registerMember() throws Exception {
        // given
        MemberDto.RegisterRequest request = MemberDto.RegisterRequest.builder()
                .memberEmail("tester@email.com")
                .memberPassword("1234")
                .memberName("테스터")
                .memberAvatar("/tester/avatar/path")
                .memberNickName("하츄핑")
                .memberSelfIntroduction("저는 테스터입니다.")
                .build();

        // when
        when(memberFacade.registerMember(any(MemberCommand.CreateMember.class))).thenReturn(MEMBER_TOKEN);

        // then
        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/api/v1/member")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", AUTH_TOKEN))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("register-member",
                        requestFields(
                                fieldWithPath("memberEmail").type(JsonFieldType.STRING).description("회원 이메일")
                                        .attributes(key("constraints").value(descriptionsForNameProperty(MemberDto.RegisterRequest.class, "memberEmail"))),
                                fieldWithPath("memberPassword").type(JsonFieldType.STRING).description("회원 로그인 비밀번호")
                                        .attributes(key("constraints").value(descriptionsForNameProperty(MemberDto.RegisterRequest.class, "memberPassword"))),
                                fieldWithPath("memberName").type(JsonFieldType.STRING).description("회원 이름")
                                        .attributes(key("constraints").value(descriptionsForNameProperty(MemberDto.RegisterRequest.class, "memberName"))),
                                fieldWithPath("memberAvatar").type(JsonFieldType.STRING).description("회원 이미지").optional()
                                        .attributes(key("constraints").value(descriptionsForNameProperty(MemberDto.RegisterRequest.class, "memberAvatar"))),
                                fieldWithPath("memberNickName").type(JsonFieldType.STRING).description("회원 별명")
                                        .attributes(key("constraints").value(descriptionsForNameProperty(MemberDto.RegisterRequest.class, "memberNickName"))),
                                fieldWithPath("memberSelfIntroduction").type(JsonFieldType.STRING).description("회원 자기소개")
                                        .attributes(key("constraints").value(descriptionsForNameProperty(MemberDto.RegisterRequest.class, "memberSelfIntroduction")))
                        ),
                        responseFields(
                                // common
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("결과"),
                                fieldWithPath("result").type(JsonFieldType.STRING).description("결과코드"),

                                // data
                                fieldWithPath("data.memberToken").type(JsonFieldType.STRING).description("회원 토큰")
                        )
                ));
    }

    @Disabled
    @Test
    @DisplayName("[회원] 단건 수정")
    void modifyMember() throws Exception {
        // given
        MemberDto.ModifyRequest request = MemberDto.ModifyRequest.builder()
                .memberName("테스터")
                .memberAvatar("/tester/avatar/path")
                .memberNickName("하츄핑")
                .memberSelfIntroduction("저는 테스터입니다.")
                .build();

        // when
        doNothing().when(memberFacade).modifyMember(isA(MemberCommand.UpdateMember.class), isA(String.class));

        // then
        this.mockMvc.perform(RestDocumentationRequestBuilders.put("/api/v1/member/{memberToken}", MEMBER_TOKEN)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", AUTH_TOKEN))
                .andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document("modify-member",
                        pathParameters(
                                parameterWithName("memberToken").description("회원 토큰")
                        ),
                        requestFields(
                                fieldWithPath("memberName").type(JsonFieldType.STRING).description("회원 이름")
                                        .attributes(key("constraints").value(descriptionsForNameProperty(MemberDto.ModifyRequest.class, "memberName"))),
                                fieldWithPath("memberAvatar").type(JsonFieldType.STRING).description("회원 이미지").optional()
                                        .attributes(key("constraints").value(descriptionsForNameProperty(MemberDto.ModifyRequest.class, "memberAvatar"))),
                                fieldWithPath("memberNickName").type(JsonFieldType.STRING).description("회원 별명")
                                        .attributes(key("constraints").value(descriptionsForNameProperty(MemberDto.ModifyRequest.class, "memberNickName"))),
                                fieldWithPath("memberSelfIntroduction").type(JsonFieldType.STRING).description("회원 자기소개")
                                        .attributes(key("constraints").value(descriptionsForNameProperty(MemberDto.ModifyRequest.class, "memberSelfIntroduction")))
                        ),
                        responseFields(
                                // common
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("결과"),
                                fieldWithPath("result").type(JsonFieldType.STRING).description("결과코드")
                        )
                ));
    }

    @Test
    @DisplayName("[회원] 단건 수정 - 회원 비밀번호")
    void modifyMemberPassword() throws Exception {
        // given
        MemberDto.ModifyMemberPasswordRequest request = MemberDto.ModifyMemberPasswordRequest.builder()
                .memberToken(MEMBER_TOKEN)
                .memberPassword("1234")
                .build();

        // when
        doNothing().when(memberFacade).modifyMemberPassword(isA(MemberCommand.UpdateMemberPassword.class), isA(String.class));

        // then
        this.mockMvc.perform(RestDocumentationRequestBuilders.patch("/api/v1/member/password")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", AUTH_TOKEN))
                .andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document("modify-member-password",
                        requestFields(
                                fieldWithPath("memberToken").type(JsonFieldType.STRING).description("회원 토큰")
                                        .attributes(key("constraints").value(descriptionsForNameProperty(MemberDto.ModifyMemberPasswordRequest.class, "memberToken"))),
                                fieldWithPath("memberPassword").type(JsonFieldType.STRING).description("회원 비밀번호")
                                        .attributes(key("constraints").value(descriptionsForNameProperty(MemberDto.ModifyMemberPasswordRequest.class, "memberPassword")))
                        ),
                        responseFields(
                                // common
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("결과"),
                                fieldWithPath("result").type(JsonFieldType.STRING).description("결과코드")
                        )
                ));
    }

    @Test
    @DisplayName("[회원] 단건 수정 - 회원 비활성화")
    void disableMember() throws Exception {
        // given
        MemberDto.ModifyMemberStatusRequest request = MemberDto.ModifyMemberStatusRequest.builder()
                .memberToken(MEMBER_TOKEN)
                .build();

        // when
        doNothing().when(memberFacade).disableMember(MEMBER_TOKEN);

        // then
        this.mockMvc.perform(RestDocumentationRequestBuilders.patch("/api/v1/member/disable")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", AUTH_TOKEN))
                .andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document("modify-member-disable",
                        requestFields(
                                fieldWithPath("memberToken").type(JsonFieldType.STRING).description("회원 토큰")
                                        .attributes(key("constraints").value(descriptionsForNameProperty(MemberDto.ModifyMemberPasswordRequest.class, "memberToken")))
                        ),
                        responseFields(
                                // common
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("결과"),
                                fieldWithPath("result").type(JsonFieldType.STRING).description("결과코드")
                        )
                ));
    }

    @Test
    @DisplayName("[회원] 단건 수정 - 회원 활성화")
    void enableMember() throws Exception {
        // given
        MemberDto.ModifyMemberStatusRequest request = MemberDto.ModifyMemberStatusRequest.builder()
                .memberToken(MEMBER_TOKEN)
                .build();

        // when
        doNothing().when(memberFacade).enableMember(MEMBER_TOKEN);

        // then
        this.mockMvc.perform(RestDocumentationRequestBuilders.patch("/api/v1/member/enable")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", AUTH_TOKEN))
                .andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document("modify-member-enable",
                        requestFields(
                                fieldWithPath("memberToken").type(JsonFieldType.STRING).description("회원 토큰")
                                        .attributes(key("constraints").value(descriptionsForNameProperty(MemberDto.ModifyMemberPasswordRequest.class, "memberToken")))
                        ),
                        responseFields(
                                // common
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("결과"),
                                fieldWithPath("result").type(JsonFieldType.STRING).description("결과코드")
                        )
                ));
    }

    @Test
    @DisplayName("[회원] 단건 삭제")
    void removeMember() throws Exception {
        // given
        MemberDto.ModifyMemberStatusRequest request = MemberDto.ModifyMemberStatusRequest.builder()
                .memberToken(MEMBER_TOKEN)
                .build();

        // when
        doNothing().when(memberFacade).removeMember(MEMBER_TOKEN);

        // then
        this.mockMvc.perform(RestDocumentationRequestBuilders.delete("/api/v1/member/{memberToken}", MEMBER_TOKEN)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", AUTH_TOKEN))
                .andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document("remove-member",
                        pathParameters(
                                parameterWithName("memberToken").description("회원 토큰")
                        ),
                        requestFields(
                                fieldWithPath("memberToken").type(JsonFieldType.STRING).description("회원 토큰")
                                        .attributes(key("constraints").value(descriptionsForNameProperty(MemberDto.ModifyMemberPasswordRequest.class, "memberToken")))
                        ),
                        responseFields(
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("결과"),
                                fieldWithPath("result").type(JsonFieldType.STRING).description("결과코드")
                        )
                ));
    }
}
