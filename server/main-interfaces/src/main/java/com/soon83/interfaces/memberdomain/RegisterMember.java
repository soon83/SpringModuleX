package com.soon83.interfaces.memberdomain;

import com.soon83.dtos.enums.MemberRole;

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
            RegisterMemberTest.validateConstructor(
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
