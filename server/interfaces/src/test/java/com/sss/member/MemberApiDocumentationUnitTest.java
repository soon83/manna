package com.sss.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sss.domain.login.LoginInfo;
import com.sss.domain.member.Member;
import com.sss.domain.member.MemberCommand;
import com.sss.domain.member.MemberQuery;
import com.sss.jwt.JwtUtil;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeAll;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
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
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private static String AUTH_TOKEN;
    private static String MEMBER_TOKEN = "mbr_Bk3GlYuJSFNWqkHw";

    @BeforeAll
    public static void login() {
        String token = JwtUtil.makeAuthToken(LoginInfo.Main.builder()
                .memberLoginId("admin")
                .memberLoginPassword(passwordEncoder.encode("1234"))
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
    @DisplayName("[??????] ?????? ??????")
    void fetchMemberList() throws Exception {
        // given
        List<MemberQuery.Main> response = Lists.newArrayList(
                MemberQuery.Main.builder()
                        .token(MEMBER_TOKEN)
                        .loginId("tester")
                        .loginPassword("1234")
                        .name("?????????")
                        .email("tester@email.com")
                        .avatar("/tester/avatar/path")
                        .nickName("?????????")
                        .selfIntroduction("?????? ??????????????????.")
                        .categoryList("[1, 2, 3, 4]")
                        .categoryItemList("[5, 6, 7, 8]")
                        .role(Member.Role.MEMBER)
                        .status(Member.Status.ENABLE)
                        .build()
        );

        // when
        when(memberFacade.fetchMemberList()).thenReturn(response);

        // then
        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/member-list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", AUTH_TOKEN))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("fetch-member-list",
                        responseFields(
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("??????"),
                                fieldWithPath("result").type(JsonFieldType.STRING).description("????????????"),
                                fieldWithPath("data.[0].memberToken").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("data.[0].memberLoginId").type(JsonFieldType.STRING).description("?????? ????????? ?????????"),
                                fieldWithPath("data.[0].memberName").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("data.[0].memberEmail").type(JsonFieldType.STRING).description("?????? ?????????"),
                                fieldWithPath("data.[0].memberAvatar").type(JsonFieldType.STRING).description("?????? ?????????"),
                                fieldWithPath("data.[0].memberNickName").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("data.[0].memberSelfIntroduction").type(JsonFieldType.STRING).description("?????? ????????????"),
                                fieldWithPath("data.[0].memberCategoryList").type(JsonFieldType.STRING).description("?????? ?????????-?????????"),
                                fieldWithPath("data.[0].memberCategoryItemList").type(JsonFieldType.STRING).description("?????? ?????????-?????????"),
                                fieldWithPath("data.[0].memberRole").type(JsonFieldType.STRING).description("?????? ?????? [MANAGER, MEMBER]"),
                                fieldWithPath("data.[0].memberStatus").type(JsonFieldType.STRING).description("?????? ????????? ?????? [ENABLE, DISABLE]")
                        )
                ));
    }

    @Test
    @DisplayName("[??????] ?????? ??????")
    void fetchMemberByMemberToken() throws Exception {
        // given
        MemberQuery.Main response = MemberQuery.Main.builder()
                .token(MEMBER_TOKEN)
                .loginId("tester")
                .loginPassword("1234")
                .name("?????????")
                .email("tester@email.com")
                .avatar("/tester/avatar/path")
                .nickName("?????????")
                .selfIntroduction("?????? ??????????????????.")
                .categoryList("[1, 2, 3, 4]")
                .categoryItemList("[5, 6, 7, 8]")
                .role(Member.Role.MEMBER)
                .status(Member.Status.ENABLE)
                .build();

        // when
        when(memberFacade.fetchMember(any(String.class))).thenReturn(response);

        // then
        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/member-list/{memberToken}", MEMBER_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", AUTH_TOKEN))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("fetch-member",
                        pathParameters(
                                parameterWithName("memberToken").description("?????? ??????")
                        ),
                        responseFields(
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("??????"),
                                fieldWithPath("result").type(JsonFieldType.STRING).description("????????????"),
                                fieldWithPath("data.memberToken").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("data.memberLoginId").type(JsonFieldType.STRING).description("?????? ????????? ?????????"),
                                fieldWithPath("data.memberName").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("data.memberEmail").type(JsonFieldType.STRING).description("?????? ?????????"),
                                fieldWithPath("data.memberAvatar").type(JsonFieldType.STRING).description("?????? ?????????"),
                                fieldWithPath("data.memberNickName").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("data.memberSelfIntroduction").type(JsonFieldType.STRING).description("?????? ????????????"),
                                fieldWithPath("data.memberCategoryList").type(JsonFieldType.STRING).description("?????? ?????????-?????????"),
                                fieldWithPath("data.memberCategoryItemList").type(JsonFieldType.STRING).description("?????? ?????????-?????????"),
                                fieldWithPath("data.memberRole").type(JsonFieldType.STRING).description("?????? ?????? [MANAGER, MEMBER]"),
                                fieldWithPath("data.memberStatus").type(JsonFieldType.STRING).description("?????? ????????? ?????? [ENABLE, DISABLE]")
                        )
                ));
    }

    @Test
    @DisplayName("[??????] ?????? ??????")
    void registerMember() throws Exception {
        // given
        MemberDto.RegisterRequest request = MemberDto.RegisterRequest.builder()
                .memberLoginId("tester")
                .memberLoginPassword("1234")
                .memberName("?????????")
                .memberEmail("tester@email.com")
                .memberAvatar("/tester/avatar/path")
                .memberNickName("?????????")
                .memberSelfIntroduction("?????? ??????????????????.")
                .memberCategoryList(Arrays.asList(1, 2, 3, 4))
                .memberCategoryItemList(Arrays.asList(5, 6, 7, 8))
                .build();

        // when
        when(memberFacade.registerMember(any(MemberCommand.CreateMember.class))).thenReturn(MEMBER_TOKEN);

        // then
        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/api/v1/member-list")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", AUTH_TOKEN))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("register-member",
                        requestFields(
                                fieldWithPath("memberLoginId").type(JsonFieldType.STRING).description("?????? ????????? ?????????")
                                        .attributes(key("constraints").value(descriptionsForNameProperty(MemberDto.RegisterRequest.class, "memberLoginId"))),
                                fieldWithPath("memberLoginPassword").type(JsonFieldType.STRING).description("?????? ????????? ????????????")
                                        .attributes(key("constraints").value(descriptionsForNameProperty(MemberDto.RegisterRequest.class, "memberLoginPassword"))),
                                fieldWithPath("memberName").type(JsonFieldType.STRING).description("?????? ??????")
                                        .attributes(key("constraints").value(descriptionsForNameProperty(MemberDto.RegisterRequest.class, "memberName"))),
                                fieldWithPath("memberEmail").type(JsonFieldType.STRING).description("?????? ?????????")
                                        .attributes(key("constraints").value(descriptionsForNameProperty(MemberDto.RegisterRequest.class, "memberEmail"))),
                                fieldWithPath("memberAvatar").type(JsonFieldType.STRING).description("?????? ?????????").optional()
                                        .attributes(key("constraints").value(descriptionsForNameProperty(MemberDto.RegisterRequest.class, "memberAvatar"))),
                                fieldWithPath("memberNickName").type(JsonFieldType.STRING).description("?????? ??????")
                                        .attributes(key("constraints").value(descriptionsForNameProperty(MemberDto.RegisterRequest.class, "memberNickName"))),
                                fieldWithPath("memberSelfIntroduction").type(JsonFieldType.STRING).description("?????? ????????????")
                                        .attributes(key("constraints").value(descriptionsForNameProperty(MemberDto.RegisterRequest.class, "memberSelfIntroduction"))),
                                fieldWithPath("memberCategoryList").type(JsonFieldType.ARRAY).description("?????? ?????????-?????????")
                                        .attributes(key("constraints").value(descriptionsForNameProperty(MemberDto.RegisterRequest.class, "memberCategoryList"))),
                                fieldWithPath("memberCategoryItemList").type(JsonFieldType.ARRAY).description("?????? ?????????-?????????")
                                        .attributes(key("constraints").value(descriptionsForNameProperty(MemberDto.RegisterRequest.class, "memberCategoryItemList")))
                        ),
                        responseFields(
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("??????"),
                                fieldWithPath("result").type(JsonFieldType.STRING).description("????????????"),
                                fieldWithPath("data.memberToken").type(JsonFieldType.STRING).description("?????? ??????")
                        )
                ));
    }

    @Test
    @DisplayName("[??????] ?????? ??????")
    void modifyMember() throws Exception {
        // given
        MemberDto.ModifyRequest request = MemberDto.ModifyRequest.builder()
                .memberName("?????????")
                .memberEmail("tester@email.com")
                .memberAvatar("/tester/avatar/path")
                .memberNickName("?????????")
                .memberSelfIntroduction("?????? ??????????????????.")
                .memberCategoryList(Arrays.asList(1, 2))
                .memberCategoryItemList(Arrays.asList(3, 4))
                .build();

        // when
        doNothing().when(memberFacade).modifyMember(isA(MemberCommand.UpdateMember.class), isA(String.class));

        // then
        this.mockMvc.perform(RestDocumentationRequestBuilders.put("/api/v1/member-list/{memberToken}", MEMBER_TOKEN)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", AUTH_TOKEN))
                .andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document("modify-member",
                        pathParameters(
                                parameterWithName("memberToken").description("?????? ??????")
                        ),
                        requestFields(
                                fieldWithPath("memberName").type(JsonFieldType.STRING).description("?????? ??????")
                                        .attributes(key("constraints").value(descriptionsForNameProperty(MemberDto.ModifyRequest.class, "memberName"))),
                                fieldWithPath("memberEmail").type(JsonFieldType.STRING).description("?????? ?????????")
                                        .attributes(key("constraints").value(descriptionsForNameProperty(MemberDto.ModifyRequest.class, "memberEmail"))),
                                fieldWithPath("memberAvatar").type(JsonFieldType.STRING).description("?????? ?????????").optional()
                                        .attributes(key("constraints").value(descriptionsForNameProperty(MemberDto.ModifyRequest.class, "memberAvatar"))),
                                fieldWithPath("memberNickName").type(JsonFieldType.STRING).description("?????? ??????")
                                        .attributes(key("constraints").value(descriptionsForNameProperty(MemberDto.ModifyRequest.class, "memberNickName"))),
                                fieldWithPath("memberSelfIntroduction").type(JsonFieldType.STRING).description("?????? ????????????")
                                        .attributes(key("constraints").value(descriptionsForNameProperty(MemberDto.ModifyRequest.class, "memberSelfIntroduction"))),
                                fieldWithPath("memberCategoryList").type(JsonFieldType.ARRAY).description("?????? ?????????-?????????")
                                        .attributes(key("constraints").value(descriptionsForNameProperty(MemberDto.ModifyRequest.class, "memberCategoryList"))),
                                fieldWithPath("memberCategoryItemList").type(JsonFieldType.ARRAY).description("?????? ?????????-?????????")
                                        .attributes(key("constraints").value(descriptionsForNameProperty(MemberDto.ModifyRequest.class, "memberCategoryItemList")))
                        ),
                        responseFields(
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("??????"),
                                fieldWithPath("result").type(JsonFieldType.STRING).description("????????????")
                        )
                ));
    }

    @Test
    @DisplayName("[??????] ?????? ?????? - ?????? ????????????")
    void modifyMemberPassword() throws Exception {
        // given
        MemberDto.ModifyMemberPasswordRequest request = MemberDto.ModifyMemberPasswordRequest.builder()
                .memberToken(MEMBER_TOKEN)
                .memberLoginPassword("1234")
                .build();

        // when
        doNothing().when(memberFacade).modifyMemberPassword(isA(MemberCommand.UpdateMemberPassword.class), isA(String.class));

        // then
        this.mockMvc.perform(RestDocumentationRequestBuilders.patch("/api/v1/member-list/password")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", AUTH_TOKEN))
                .andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document("modify-member-password",
                        requestFields(
                                fieldWithPath("memberToken").type(JsonFieldType.STRING).description("?????? ??????")
                                        .attributes(key("constraints").value(descriptionsForNameProperty(MemberDto.ModifyMemberPasswordRequest.class, "memberToken"))),
                                fieldWithPath("memberLoginPassword").type(JsonFieldType.STRING).description("?????? ????????????")
                                        .attributes(key("constraints").value(descriptionsForNameProperty(MemberDto.ModifyMemberPasswordRequest.class, "memberLoginPassword")))
                        ),
                        responseFields(
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("??????"),
                                fieldWithPath("result").type(JsonFieldType.STRING).description("????????????")
                        )
                ));
    }

    @Test
    @DisplayName("[??????] ?????? ?????? - ?????? ????????????")
    void disableMember() throws Exception {
        // given
        MemberDto.ModifyMemberStatusRequest request = MemberDto.ModifyMemberStatusRequest.builder()
                .memberToken(MEMBER_TOKEN)
                .build();

        // when
        doNothing().when(memberFacade).disableMember(MEMBER_TOKEN);

        // then
        this.mockMvc.perform(RestDocumentationRequestBuilders.patch("/api/v1/member-list/disable")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", AUTH_TOKEN))
                .andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document("modify-member-disable",
                        requestFields(
                                fieldWithPath("memberToken").type(JsonFieldType.STRING).description("?????? ??????")
                                        .attributes(key("constraints").value(descriptionsForNameProperty(MemberDto.ModifyMemberPasswordRequest.class, "memberToken")))
                        ),
                        responseFields(
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("??????"),
                                fieldWithPath("result").type(JsonFieldType.STRING).description("????????????")
                        )
                ));
    }

    @Test
    @DisplayName("[??????] ?????? ?????? - ?????? ?????????")
    void enableMember() throws Exception {
        // given
        MemberDto.ModifyMemberStatusRequest request = MemberDto.ModifyMemberStatusRequest.builder()
                .memberToken(MEMBER_TOKEN)
                .build();

        // when
        doNothing().when(memberFacade).enableMember(MEMBER_TOKEN);

        // then
        this.mockMvc.perform(RestDocumentationRequestBuilders.patch("/api/v1/member-list/enable")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", AUTH_TOKEN))
                .andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document("modify-member-enable",
                        requestFields(
                                fieldWithPath("memberToken").type(JsonFieldType.STRING).description("?????? ??????")
                                        .attributes(key("constraints").value(descriptionsForNameProperty(MemberDto.ModifyMemberPasswordRequest.class, "memberToken")))
                        ),
                        responseFields(
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("??????"),
                                fieldWithPath("result").type(JsonFieldType.STRING).description("????????????")
                        )
                ));
    }

    @Test
    @DisplayName("[??????] ?????? ??????")
    void removeMember() throws Exception {
        // given
        MemberDto.ModifyMemberStatusRequest request = MemberDto.ModifyMemberStatusRequest.builder()
                .memberToken(MEMBER_TOKEN)
                .build();

        // when
        doNothing().when(memberFacade).removeMember(MEMBER_TOKEN);

        // then
        this.mockMvc.perform(RestDocumentationRequestBuilders.delete("/api/v1/member-list/{memberToken}", MEMBER_TOKEN)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", AUTH_TOKEN))
                .andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document("remove-member",
                        pathParameters(
                                parameterWithName("memberToken").description("?????? ??????")
                        ),
                        requestFields(
                                fieldWithPath("memberToken").type(JsonFieldType.STRING).description("?????? ??????")
                                        .attributes(key("constraints").value(descriptionsForNameProperty(MemberDto.ModifyMemberPasswordRequest.class, "memberToken")))
                        ),
                        responseFields(
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("??????"),
                                fieldWithPath("result").type(JsonFieldType.STRING).description("????????????")
                        )
                ));
    }
}
