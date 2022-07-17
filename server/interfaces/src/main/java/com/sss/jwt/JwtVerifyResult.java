package com.sss.jwt;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtVerifyResult {
    private boolean success;
    private String username;
}
