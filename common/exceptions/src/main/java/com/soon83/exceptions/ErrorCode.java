package com.soon83.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // [시스템 공통]
    SYSTEM_ERROR(500, "COM-001", "일시적인 오류가 발생하였습니다. 잠시 후 다시 시도해주세요."),
    ARGUMENT_NOT_VALID_ERROR(400, "COM-002", "올바르지 않은 파라미터입니다."),
    REQUEST_JSON_PARSE_ERROR(400, "COM-003", "올바르지 않은 포맷입니다."),
    ARGUMENT_TYPE_MISMATCH_ERROR(400, "COM-004", "올바르지 않은 파라미터입니다."),
    ILLEGAL_ARGUMENT_ERROR(400, "COM-005", "필수 파라미터가 없습니다."),
    COMMON_INVALID_TOKEN_ERROR(401, "COM-006", "유효하지 않은 토큰입니다."),
    FORBIDDEN_ERROR(403, "COM-007", "접근이 불가합니다."),
    NOT_FOUND_USER_ERROR(404, "COM-008", "존재하지 않는 사용자 입니다."),
    METHOD_NOT_ALLOWED_ERROR(405, "COM-009", "지원하지 않는 메서드입니다."),
    FOREIGN_KEY_CONSTRAINT_VIOLATION(400, "COM-010", "다른 곳에서 사용중인 데이터이므로 삭제가 불가능합니다."),
    UNIQUE_CONSTRAINT_VIOLATION(409, "COM-011", "이미 존재하는 데이터입니다."),
    DATA_INTEGRITY_VIOLATION(400, "COM-012", "데이터에 문제가 있습니다. 확인 후 다시 시도해주세요."),

    // [인증]
    UNAUTHORIZED_ERROR(401, "AUTH-001", "인증이 필요합니다."),

    // [회원]
    NOT_FOUND_MEMBER(404, "MEMBER-001", "존재하지 않는 회원 입니다."),
    ;

    private final int status;
    private final String code;
    private final String message;
}
