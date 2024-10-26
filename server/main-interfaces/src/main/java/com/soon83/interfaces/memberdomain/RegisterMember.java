package com.soon83.interfaces.memberdomain;

import com.soon83.dtos.enums.MemberRole;
import org.springframework.util.Assert;

public class RegisterMember {
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
    }
}
