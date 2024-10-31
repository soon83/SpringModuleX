package com.soon83.interfaces.member;

import com.soon83.dtos.enums.MemberRole;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberSearchRequest {
    private Long memberId;

    @Size(max = 63, message = "회원 로그인 아이디의 최대 길이는 63 입니다.")
    private String loginId;

    @Size(max = 255, message = "회원 비밀번호의 최대 길이는 255 입니다.")
    private String password;

    @Size(max = 31, message = "회원 이름의 최대 길이는 31 입니다.")
    private String name;

    @Size(max = 31, message = "회원 이메일의 최대 길이는 31 입니다.")
    private String email;

    private MemberRole role;

}
