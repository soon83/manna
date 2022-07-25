package com.sss.api;

import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IndexApiControllerTest {
    private WebTestClient webTestClient;

    @LocalServerPort
    private Integer port;

    @BeforeEach
    public void setUpRestdocs(RestDocumentationContextProvider restDocumentation) {
        this.webTestClient = WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + port)
                .build();
    }

    @Test
    @DisplayName("[http-basic] user 가 user API 를 호출")
    public void callUserApiByUser() {
        String header = Base64.encodeBase64String("user:1234".getBytes(StandardCharsets.UTF_8));

        this.webTestClient.get().uri("/api/user")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Basic " + header)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("[http-basic] user 가 admin API 를 호출")
    public void callAdminApiByUser() {
        String basicToken = Base64.encodeBase64String("user:1234".getBytes(StandardCharsets.UTF_8));

        this.webTestClient.get().uri("/api/admin")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Basic " + basicToken)
                .exchange()
                .expectStatus().isForbidden();
    }
}