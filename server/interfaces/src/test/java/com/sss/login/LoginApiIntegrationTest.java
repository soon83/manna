package com.sss.login;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import static com.sss.restdocs.ApiDocumentUtils.uriModifyingOperationPreprocessor;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.documentationConfiguration;

@ActiveProfiles("local")
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginApiIntegrationTest {
    private WebTestClient webTestClient;

    @LocalServerPort
    private Integer port;

    @BeforeEach
    public void setUpRestdocs(RestDocumentationContextProvider restDocumentation) {
        this.webTestClient = WebTestClient.bindToServer()
                .baseUrl("http://127.0.0.1:" + port)
                .filter(documentationConfiguration(restDocumentation)
                        .operationPreprocessors()
                        .withRequestDefaults(uriModifyingOperationPreprocessor(), prettyPrint())
                        .withResponseDefaults(prettyPrint()))
                .build();
    }

    @Test
    @DisplayName("[?????????] ??????")
    public void login() throws Exception {
        // given
        LoginDto.LoginRequest request = LoginDto.LoginRequest.builder()
                .memberLoginId("admin")
                .memberLoginPassword("1234")
                .build();

        this.webTestClient.post().uri("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(
                        document("login",
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
                        )
                );
    }
}
