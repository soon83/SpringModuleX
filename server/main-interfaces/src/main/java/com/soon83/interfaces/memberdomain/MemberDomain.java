package com.soon83.interfaces.memberdomain;

import com.soon83.dtos.enums.MemberRole;

public class MemberDomain {
    private final String loginId;
    private final String password;
    private final String name;
    private final String email;
    private final MemberRole memberRole;
    private Long memberId;

    public MemberDomain(
            String loginId,
            String password,
            String name,
            String email,
            MemberRole memberRole
    ) {
        RegisterMemberTest.validateConstructor(
                loginId,
                password,
                name,
                email,
                memberRole
        );
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.memberRole = memberRole;
    }

    public void assignId(Long nextId) {
        this.memberId = nextId;
    }

    public Long getMemberId() {
        return memberId;
    }
}
