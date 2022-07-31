package com.sss.exception.category;

import com.sss.exception.common.BaseException;
import com.sss.exception.ErrorCode;

public class CategoryNotFoundException extends BaseException {

    public CategoryNotFoundException() {
        super(ErrorCode.CATEGORY_NOT_FOUND);
    }

    public CategoryNotFoundException(String message) {
        super(message, ErrorCode.CATEGORY_NOT_FOUND);
    }
}