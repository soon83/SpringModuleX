package com.soon83.exceptions;

import lombok.Getter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ErrorResponse {
    private final boolean success = false;
    private final int status;
    private final String code;
    private final String message;
    private final List<BindError> errors;
    private final LocalDateTime timestamp = LocalDateTime.now();

    private ErrorResponse(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.errors = new ArrayList<>();
    }

    private ErrorResponse(int status, String code, String message, BindingResult bindingResult) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.errors = BindError.of(bindingResult);
    }

    public static ErrorResponse of(int status, String code, String message) {
        return new ErrorResponse(status, code, message);
    }

    public static ErrorResponse of(int status, String code, String message, BindingResult bindingResult) {
        return new ErrorResponse(status, code, message, bindingResult);
    }

    @Getter
    static class BindError {
        private final String field;
        private final String value;
        private final String message;

        private BindError(FieldError fieldError) {
            this.field = fieldError.getField();
            this.value = fieldError.getRejectedValue() == null ? "" : fieldError.getRejectedValue().toString();
            this.message = fieldError.getDefaultMessage();
        }

        public static List<BindError> of(BindingResult bindingResult) {
            return bindingResult.getFieldErrors().stream()
                    .map(BindError::new)
                    .toList();
        }
    }
}
