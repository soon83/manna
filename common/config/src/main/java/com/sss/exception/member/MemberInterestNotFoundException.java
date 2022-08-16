package com.sss.exception.member;

import com.sss.exception.ErrorCode;
import com.sss.exception.common.BaseException;

public class MemberInterestNotFoundException extends BaseException {

    public MemberInterestNotFoundException() {
        super(ErrorCode.MEMBER_INTEREST_NOT_FOUND);
    }

    public MemberInterestNotFoundException(String message) {
        super(message, ErrorCode.MEMBER_INTEREST_NOT_FOUND);
    }
}
