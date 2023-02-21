package com.sss.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sss.domain.login.LoginInfo;

import java.time.Instant;

public class JwtUtil {
    public static final String BEARER_TOKEN_PREFIX = "Bearer ";
    private static final Algorithm ALGORITHM = Algorithm.HMAC256("사랑의하츄핑");
    private static final long AUTH_TIME = 60 * 30; // 30분
    private static final long REFRESH_TIME = 60 * 60 * 24 * 14; // 2주

    public static String makeAuthToken(LoginInfo.Main user) {
        return JWT.create()
                .withSubject(user.getMemberEmail())
                .withClaim("exp", Instant.now().getEpochSecond() + AUTH_TIME)
                .sign(ALGORITHM);
    }

    public static String makeRefreshToken(LoginInfo.Main user) {
        return JWT.create()
                .withSubject(user.getMemberEmail())
                .withClaim("exp", Instant.now().getEpochSecond() + REFRESH_TIME)
                .sign(ALGORITHM);
    }

    public static JwtVerifyResult verify(String token) {
        try {
            DecodedJWT verify = JWT.require(ALGORITHM).build().verify(token);
            return JwtVerifyResult.builder()
                    .success(true)
                    .username(verify.getSubject())
                    .build();
        } catch (Exception ex) {
            DecodedJWT decode = JWT.decode(token);
            return JwtVerifyResult.builder()
                    .success(false)
                    .username(decode.getSubject())
                    .build();
        }
    }
}
