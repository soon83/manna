package com.sss.exception.member;

import com.sss.exception.common.BaseException;
import com.sss.exception.ErrorCode;

public class MemberNotFoundException extends BaseException {

    public MemberNotFoundException() {
        super(ErrorCode.MEMBER_NOT_FOUND);
    }

    public MemberNotFoundException(String message) {
        super(message, ErrorCode.MEMBER_NOT_FOUND);
    }
}