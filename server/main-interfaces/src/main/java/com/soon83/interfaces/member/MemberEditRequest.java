package com.soon83.interfaces.member;

import com.soon83.dtos.enums.MemberRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberEditRequest {
    @NotNull(message = "회원 아이디는 필수값 입니다.")
    private Long memberId;

    @NotBlank(message = "회원 로그인 아이디는 필수값 입니다.")
    private String loginId;

    @NotBlank(message = "회원 비밀번호는 필수값 입니다.")
    private String password;

    @NotBlank(message = "회원 이름은 필수값 입니다.")
    private String name;

    @NotBlank(message = "회원 이메일은 필수값 입니다.")
    private String email;

    @NotNull(message = "회원 유형은 필수값 입니다.")
    private MemberRole role;
}
