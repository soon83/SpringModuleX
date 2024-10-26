package com.soon83.infrastructure.member;

import com.soon83.entities.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberStore {
    private final MemberRepository memberRepository;

    public Member create(Member member) {
        return memberRepository.save(member);
    }

    public void update(Member member) {
        memberRepository.save(member);
    }

    public void delete(Member member) {
        memberRepository.delete(member);
    }
}