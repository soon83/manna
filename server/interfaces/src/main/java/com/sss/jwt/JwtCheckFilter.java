package com.sss.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sss.domain.login.LoginInfo;
import com.sss.domain.login.LoginService;
import com.sss.exception.ErrorCode;
import com.sss.exception.ErrorRes;
import com.sss.response.Res;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtCheckFilter extends BasicAuthenticationFilter {
    private final LoginService loginService;
    private final ObjectMapper objectMapper;

    public JwtCheckFilter(AuthenticationManager authenticationManager, LoginService loginService, ObjectMapper objectMapper) {
        super(authenticationManager);
        this.loginService = loginService;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String bearer = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (bearer == null || !bearer.startsWith(JwtUtil.BEARER_TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        String token = bearer.substring(JwtUtil.BEARER_TOKEN_PREFIX.length());
        var result = JwtUtil.verify(token);
        if (result.isSuccess()) {
            var member = (LoginInfo.AccountAdaptor) loginService.loadUserByUsername(result.getUsername());
            var userToken = new UsernamePasswordAuthenticationToken(member.getLoginUser(), null, member.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(userToken);
            chain.doFilter(request, response);
        } else {
            //throw new AuthenticationException("유효하지 않은 토큰입니다.");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            ErrorRes errorResponse = ErrorRes.of(ErrorCode.COMMON_INVALID_TOKEN);
            response.getOutputStream().write(objectMapper.writeValueAsBytes(Res.fail(errorResponse)));
        }
    }
}
