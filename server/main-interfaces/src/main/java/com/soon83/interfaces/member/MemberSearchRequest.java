package com.soon83.interfaces.member;

import com.soon83.dtos.enums.MemberRole;
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
    private String loginId;
    private String password;
    private String name;
    private String email;
    private MemberRole role;

    public String getRoleTitle() {
        return role.getTitle();
    }
}