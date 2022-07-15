package com.sss.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // 000. 공통 에러코드
    COMMON_SYSTEM_ERROR("C001", "일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요."), // 장애 상황
    COMMON_INVALID_PARAMETER("C002", "요청한 값이 올바르지 않습니다."),
    COMMON_NOT_FOUND("C003", "요청한 주소가 올바르지 않습니다."),
    COMMON_METHOD_NOT_ALLOWED("C004", "지원하지 않는 메서드입니다."),
    COMMON_ILLEGAL_STATUS("C005", "잘못된 상태값입니다."),
    COMMON_UNAUTHORIZED("C006", "인증오류 입니다."),
    COMMON_FORBIDDEN("C007", "접근권한이 없습니다."),

    // 100. 회원 에러코드
    MEMBER_NOT_FOUND("M101", "존재하지 않는 회원입니다."),

    // 200. 카테고리 에러코드
    CATEGORY_NOT_FOUND("M201", "존재하지 않는 카테고리입니다."),
    ;

    private final String code;
    private final String message;
}