package com.soon83.interfaces.memberdomain;

import com.soon83.dtos.enums.MemberRole;
import org.springframework.util.Assert;

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
        validateConstructor(
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

    public void validateConstructor(
            String loginId,
            String password,
            String name,
            String email,
            MemberRole memberRole
    ) {
        Assert.hasText(loginId, "로그인 아이디는 필수값 입니다.");
        Assert.hasText(password, "비밀번호는 필수값 입니다.");
        Assert.hasText(name, "이름은 필수값 입니다.");
        Assert.hasText(email, "이메일은 필수값 입니다.");
        Assert.notNull(memberRole, "권한은 필수값 입니다.");
    }

    public void assignId(Long nextId) {
        this.memberId = nextId;
    }

    public Long getMemberId() {
        return memberId;
    }
}
