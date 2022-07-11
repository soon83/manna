package com.sss.category;

import com.sss.common.exception.BaseException;
import com.sss.common.response.ErrorCode;

public class CategoryNotFoundException extends BaseException {

    public CategoryNotFoundException() {
        super(ErrorCode.CATEGORY_NOT_FOUND);
    }

    public CategoryNotFoundException(String message) {
        super(message, ErrorCode.CATEGORY_NOT_FOUND);
    }
}