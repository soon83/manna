package com.sss.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sss.domain.member.MemberAuth;

import java.time.Instant;

public class JwtUtil {

    public static final String BEARER_TOKEN_PREFIX = "Bearer ";
    private static final Algorithm ALGORITHM = Algorithm.HMAC256("사랑의하츄핑");
    private static final long AUTH_TIME = 20 * 60;
    private static final long REFRESH_TIME = 60 * 60 * 24 * 7;

    public static String makeAuthToken(MemberAuth.Main user) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withClaim("exp", Instant.now().getEpochSecond() + AUTH_TIME)
                .sign(ALGORITHM);
    }

    public static String makeRefreshToken(MemberAuth.Main user) {
        return JWT.create()
                .withSubject(user.getUsername())
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
