package com.sss.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sss.common.response.ErrorCode;
import com.sss.common.response.ErrorRes;
import com.sss.common.response.Res;
import com.sss.domain.member.MemberAuth;
import com.sss.exception.member.MemberNotFoundException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.sss.config.SecurityConfig.LOGIN;

@Slf4j
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public JwtLoginFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        setFilterProcessesUrl(LOGIN);
    }

    @Override
    @SneakyThrows
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        MemberAuth.Main user = objectMapper.readValue(request.getInputStream(), MemberAuth.Main.class);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                user.getUsername(), user.getPassword(), null
        );

        // user details...
        return getAuthenticationManager().authenticate(token);
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    ) throws IOException {
        MemberAuth.Main user = (MemberAuth.Main) authResult.getPrincipal();
        // TODO MemberAuth.Main -> MemberDto 로 변환해서 응답하든지 아님 MemberAdapter 만들자 OK?

        response.setHeader(HttpHeaders.AUTHORIZATION, JwtUtil.BEARER_TOKEN_PREFIX + JwtUtil.makeAuthToken(user));
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.getOutputStream().write(objectMapper.writeValueAsBytes(user));
    }

    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException failed
    ) throws IOException {
        /**
         * MEMBER_NOT_FOUND
         */
        if (failed.getCause() instanceof MemberNotFoundException) {
            response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            ErrorRes errorResponse = ErrorRes.of(ErrorCode.MEMBER_NOT_FOUND);
            objectMapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);
            response.getOutputStream().write(objectMapper.writeValueAsBytes(Res.fail(errorResponse)));
        }
    }
}
