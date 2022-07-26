package com.sss.api;

import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.nio.charset.StandardCharsets;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IndexApiControllerTest {
    private WebTestClient webTestClient;
    private static String VALID_BASIC_TOKEN = "Basic" + Base64.encodeBase64String("user:1234".getBytes(StandardCharsets.UTF_8));
    private static String INVALID_BASIC_TOKEN = "Basic" + Base64.encodeBase64String("user:0000".getBytes(StandardCharsets.UTF_8));

    @LocalServerPort
    private Integer port;

    @BeforeEach
    public void setUpRestdocs() {
        this.webTestClient = WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + port)
                .build();
    }

    @Test
    @DisplayName("[http-basic] user 가 user API 를 호출")
    public void callUserApiByUser() {
        this.webTestClient.get().uri("/api/user")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", VALID_BASIC_TOKEN)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("[http-basic] user 가 admin API 를 호출")
    public void callAdminApiByUser() {
        this.webTestClient.get().uri("/api/admin")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", VALID_BASIC_TOKEN)
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    @DisplayName("[http-basic] id:password 가 일치하지 않을 때")
    public void invalidUserInfo() {
        this.webTestClient.get().uri("/api/user")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", INVALID_BASIC_TOKEN)
                .exchange()
                .expectStatus().isUnauthorized();
    }
}