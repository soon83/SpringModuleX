package com.soon83.interfaces.main.member;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberRemoveRequest {
    @NotNull(message = "회원 아이디는 필수값 입니다.")
    private Long memberId;
}
