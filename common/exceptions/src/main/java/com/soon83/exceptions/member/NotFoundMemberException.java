package com.soon83.exceptions.member;

import com.soon83.exceptions.ErrorCode;
import com.soon83.exceptions.ServerException;

public class NotFoundMemberException extends ServerException {
    public NotFoundMemberException() {
        super(ErrorCode.NOT_FOUND_MEMBER);
    }
}
