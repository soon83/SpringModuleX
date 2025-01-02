package com.soon83.interfaces.main.exceptions;

import com.soon83.exceptions.ErrorCode;
import com.soon83.exceptions.ErrorResponse;
import com.soon83.exceptions.ServerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("[Exception] cause: {}", NestedExceptionUtils.getMostSpecificCause(e), e);
        ErrorCode errorCode = ErrorCode.SYSTEM_ERROR;
        ErrorResponse errorResponse = ErrorResponse.of(errorCode.getStatus(), errorCode.getCode(), errorCode.getMessage());
        return ResponseEntity.status(ErrorCode.SYSTEM_ERROR.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(ServerException.class)
    protected ResponseEntity<ErrorResponse> handleServerException(ServerException e) {
        log.error("[ServerException] cause: {}", NestedExceptionUtils.getMostSpecificCause(e), e);
        ErrorCode errorCode = e.getErrorCode();
        ErrorResponse errorResponse = ErrorResponse.of(errorCode.getStatus(), errorCode.getCode(), errorCode.getMessage());
        return ResponseEntity.status(errorCode.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("[IllegalArgumentException] cause: {}", NestedExceptionUtils.getMostSpecificCause(e), e);
        ErrorCode errorCode = ErrorCode.ILLEGAL_ARGUMENT_ERROR;
        ErrorResponse errorResponse = ErrorResponse.of(
                errorCode.getStatus(),
                errorCode.getCode(),
                String.format("%s", NestedExceptionUtils.getMostSpecificCause(e).getMessage()));
        return ResponseEntity.status(errorCode.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("[MethodArgumentNotValidException] cause: {}", NestedExceptionUtils.getMostSpecificCause(e), e);
        ErrorCode errorCode = ErrorCode.ARGUMENT_NOT_VALID_ERROR;
        ErrorResponse errorResponse = ErrorResponse.of(errorCode.getStatus(), errorCode.getCode(), errorCode.getMessage(), e.getBindingResult());
        return ResponseEntity.status(errorCode.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("[HttpMessageNotReadableException] cause: {}", NestedExceptionUtils.getMostSpecificCause(e), e);
        ErrorCode errorCode = ErrorCode.REQUEST_JSON_PARSE_ERROR;
        ErrorResponse errorResponse = ErrorResponse.of(errorCode.getStatus(), errorCode.getCode(), errorCode.getMessage());
        return ResponseEntity.status(errorCode.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("[MissingServletRequestParameterException] cause: {}", NestedExceptionUtils.getMostSpecificCause(e), e);
        ErrorCode errorCode = ErrorCode.ILLEGAL_ARGUMENT_ERROR;
        ErrorResponse errorResponse = ErrorResponse.of(errorCode.getStatus(), errorCode.getCode(), errorCode.getMessage());
        return ResponseEntity.status(errorCode.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("[HttpRequestMethodNotSupportedException] cause: {}", NestedExceptionUtils.getMostSpecificCause(e), e);
        ErrorCode errorCode = ErrorCode.METHOD_NOT_ALLOWED_ERROR;
        ErrorResponse errorResponse = ErrorResponse.of(errorCode.getStatus(), errorCode.getCode(), errorCode.getMessage());
        return ResponseEntity.status(errorCode.getStatus()).body(errorResponse);
    }
}