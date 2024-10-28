package com.soon83.interfaces.member;

import com.soon83.dtos.enums.MemberRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class MemberRegisterRequest {
    @NotBlank(message = "회원 로그인 아이디는 필수값 입니다.")
    private final String loginId;

    @NotBlank(message = "회원 비밀번호는 필수값 입니다.")
    private final String password;

    @NotBlank(message = "회원 이름은 필수값 입니다.")
    private final String name;

    @NotBlank(message = "회원 이메일은 필수값 입니다.")
    private final String email;

    @NotNull(message = "회원 유형은 필수값 입니다.")
    private final MemberRole role;
}
