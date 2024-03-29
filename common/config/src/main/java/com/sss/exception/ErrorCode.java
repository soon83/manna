package com.sss.exception;

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
    COMMON_UNAUTHORIZED("C006", "유효한 자격증명이 없습니다."), // 401
    COMMON_FORBIDDEN("C007", "접근권한이 없습니다."), // 403
    COMMON_INVALID_TOKEN("C008", "유효하지 않은 토큰입니다."),

    // 100. 회원 에러코드
    MEMBER_NOT_FOUND("M101", "존재하지 않는 회원입니다."),

    // 200. 회원 관심사 에러코드
    MEMBER_INTEREST_NOT_FOUND("M201", "존재하지 않는 관심사 항목 입니다."),

    // 300. 관심주제 에러코드
    CATEGORY_NOT_FOUND("M301", "존재하지 않는 관심사 주제 입니다."),

    // 400. 관심항목 에러코드
    CATEGORY_ITEM_NOT_FOUND("M401", "존재하지 않는 관심사 항목 입니다."),
    ;

    private final String code;
    private final String message;
}
