package com.soon83.exceptions;

import lombok.Getter;

@Getter
public class ServerException extends RuntimeException {
    private ErrorCode errorCode;

    public ServerException(Throwable cause) {
        super(cause);
    }

    public ServerException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ServerException(Throwable cause, ErrorCode errorCode) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }

    public ServerException(Throwable cause, ErrorCode errorCode, String customMessage) {
        super(customMessage, cause);
        this.errorCode = errorCode;
    }
}
