package com.sss.exception.category;

import com.sss.exception.common.BaseException;
import com.sss.exception.ErrorCode;

public class CategoryItemNotFoundException extends BaseException {

    public CategoryItemNotFoundException() {
        super(ErrorCode.CATEGORY_ITEM_NOT_FOUND);
    }

    public CategoryItemNotFoundException(String message) {
        super(message, ErrorCode.CATEGORY_ITEM_NOT_FOUND);
    }
}