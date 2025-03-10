package com.soon83.interfaces.main.member;

import com.soon83.dtos.enums.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class MemberInfoResponse {
    private Long memberId;

    private String loginId;

    private String password;

    private String name;

    private String email;

    private MemberRole role;

    public String getRoleTitle() {
        return role.getTitle();
    }
}
