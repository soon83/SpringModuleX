package com.soon83.interfaces.member;

import com.soon83.dtos.enums.MemberRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RegisterMemberTest {
    private RegisterMember registerMember;

    @BeforeEach
    void setUp() {
        registerMember = new RegisterMember();
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
//        Assertions.assertThat(memberRepository.findAll()).isEqualTo();
    }

    public static class RegisterMember {
        public void request(RegisterMember.Request registerMember) {

        }

        public record Request(
                String loginId,
                String password,
                String name,
                String email,
                MemberRole memberRole
        ) {
        }
    }
}
