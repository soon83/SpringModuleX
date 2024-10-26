package com.soon83.interfaces.member;

import com.soon83.dtos.enums.MemberRole;
import com.soon83.interfaces.memberdomain.MemberRepository;
import com.soon83.interfaces.memberdomain.RegisterMember;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import static org.assertj.core.api.Assertions.assertThat;

class RegisterMemberTest {
    private RegisterMember registerMember;
    private MemberRepository memberRepository;

    public static void validateConstructor(
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

    @BeforeEach
    void setUp() {
        memberRepository = new MemberRepository();
        registerMember = new RegisterMember(memberRepository);
    }

    @Test
    @DisplayName("회원을 등록한다")
    void registerMember() {
        // given
        String loginId = "loginId";
        String password = "password";
        String name = "name";
        String email = "email";
        MemberRole memberRole = MemberRole.MEMBER;
        RegisterMember.Request request = new RegisterMember.Request(
                loginId,
                password,
                name,
                email,
                memberRole
        );

        // when
        registerMember.request(request);

        // then
        assertThat(memberRepository.findAll()).hasSize(1);
    }
}
