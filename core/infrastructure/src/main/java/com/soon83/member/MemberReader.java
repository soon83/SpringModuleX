package com.soon83.member;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberReader {
	private final MemberRepository memberRepository;

	public Member getMemberOrThrow(Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new RuntimeException("조회된 회원이 없습니다."));
	}

	public Member getMemberOrNull(Long memberId) {
		return memberRepository.findById(memberId)
			.orElse(null);
	}
}
