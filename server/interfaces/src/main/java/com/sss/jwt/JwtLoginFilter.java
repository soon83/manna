package com.sss.jwt;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sss.domain.login.LoginInfo;
import com.sss.exception.member.MemberNotFoundException;
import com.sss.login.LoginDto;
import com.sss.response.ErrorCode;
import com.sss.response.ErrorRes;
import com.sss.response.Res;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
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
    private final ObjectMapper objectMapper;

    public JwtLoginFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        setFilterProcessesUrl(LOGIN);
        this.objectMapper = new ObjectMapper().setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);
    }

    @Override
    @SneakyThrows
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        var authRequest = objectMapper.readValue(request.getInputStream(), LoginDto.LoginRequest.class);
        var token = new UsernamePasswordAuthenticationToken(authRequest.getMemberLoginId(), authRequest.getMemberLoginPassword(), null);
        return getAuthenticationManager().authenticate(token);
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    ) throws IOException {
        var accountAdaptor = (LoginInfo.AccountAdaptor) authResult.getPrincipal();
        var memberLoginInfo = accountAdaptor.getMemberLoginInfo();
        var authResponse = new LoginDto.MainResponse(memberLoginInfo);
        response.setHeader(HttpHeaders.AUTHORIZATION, JwtUtil.BEARER_TOKEN_PREFIX + JwtUtil.makeAuthToken(memberLoginInfo));
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.getOutputStream().write(objectMapper.writeValueAsBytes(Res.success(authResponse)));
    }

    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException failed
    ) throws IOException {
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        ErrorRes errorResponse = ErrorRes.of(ErrorCode.COMMON_SYSTEM_ERROR);

        if (failed.getCause() instanceof MemberNotFoundException || failed instanceof BadCredentialsException) {
            errorResponse = ErrorRes.of(ErrorCode.MEMBER_NOT_FOUND);
        }

        response.getOutputStream().write(objectMapper.writeValueAsBytes(Res.fail(errorResponse)));
    }
}
