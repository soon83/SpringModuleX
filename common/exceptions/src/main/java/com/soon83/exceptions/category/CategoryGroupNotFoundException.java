package com.soon83.exceptions.category;

import com.soon83.exceptions.ErrorCode;
import com.soon83.exceptions.ServerException;

public class CategoryGroupNotFoundException extends ServerException {
    public CategoryGroupNotFoundException() {
        super(ErrorCode.NOT_FOUND_CATEGORY_GROUP);
    }
}
