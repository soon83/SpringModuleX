package com.soon83.interfaces.member;

import com.soon83.dtos.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class MemberRegisterRequest {
    private final String loginId;
    private final String password;
    private final String name;
    private final String email;
    private final UserRole role;
}
