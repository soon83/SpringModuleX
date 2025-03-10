package com.soon83.dtos.member;

import com.soon83.dtos.enums.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class MemberUpdateCommand {
    private final Long memberId;
    private final String loginId;
    private final String password;
    private final String name;
    private final String email;
    private final MemberRole role;
}
