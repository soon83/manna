package com.sss.security;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sss.login.LoginDto;
import com.sss.response.ErrorCode;
import com.sss.response.ErrorRes;
import com.sss.response.Res;
import com.sss.domain.login.LoginInfo;
import com.sss.exception.member.MemberNotFoundException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.sss.security.SecurityConfig.LOGIN;

@Slf4j
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper;

    public JwtLoginFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        setFilterProcessesUrl(LOGIN);
        this.objectMapper = new ObjectMapper();
        this.objectMapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);
    }

    @Override
    @SneakyThrows
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        LoginDto.AuthRequest authRequest = objectMapper.readValue(request.getInputStream(), LoginDto.AuthRequest.class);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                authRequest.getMemberLoginId(), authRequest.getMemberLoginPassword(), null
        );
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
        response.setHeader(HttpHeaders.AUTHORIZATION, JwtUtil.BEARER_TOKEN_PREFIX + JwtUtil.makeAuthToken(memberLoginInfo));
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        var authResponse = new LoginDto.MainResponse(memberLoginInfo);
        response.getOutputStream().write(objectMapper.writeValueAsBytes(Res.success(authResponse)));
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
            response.getOutputStream().write(objectMapper.writeValueAsBytes(Res.fail(errorResponse)));
        }
    }
}