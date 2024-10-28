package com.soon83.interfaces.member;

import com.soon83.dtos.enums.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class MemberEditRequest {
    private final String memberId;
    private final String loginId;
    private final String password;
    private final String name;
    private final String email;
    private final MemberRole role;
}
