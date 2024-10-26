package com.soon83.interfaces.member;

import com.soon83.dtos.enums.MemberRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static class RegisterMember {
        private final MemberRepository memberRepository;

        public RegisterMember(final MemberRepository memberRepository) {
            this.memberRepository = memberRepository;
        }

        public void request(final Request request) {
            // request 에서 필요한 값을 꺼내서 회원 도메인을 생성하고 저장한다.
            MemberDomain member = request.toDomain();
            memberRepository.save(member);
        }

        public record Request(
                String loginId,
                String password,
                String name,
                String email,
                MemberRole memberRole
        ) {
            public Request {
                validateConstructor(
                        loginId,
                        password,
                        name,
                        email,
                        memberRole
                );
            }

            public MemberDomain toDomain() {
                return new MemberDomain(
                        loginId,
                        password,
                        name,
                        email,
                        memberRole
                );
            }
        }
    }

    public static class MemberDomain {
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

        public void assignId(Long nextId) {
            this.memberId = nextId;
        }

        public Long getMemberId() {
            return memberId;
        }
    }

    public static class MemberRepository {
        private final Map<Long, MemberDomain> memberDomains = new HashMap<>();
        private Long nextId = 1L;

        public void save(MemberDomain member) {
            // 회원 저장
            member.assignId(nextId++);
            memberDomains.put(member.getMemberId(), member);
        }

        public List<MemberDomain> findAll() {
            return new ArrayList<>(memberDomains.values());
        }
    }
}
