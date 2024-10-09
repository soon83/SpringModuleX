package com.soon83.member;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberFacade {
	private final MemberService memberService;

	public MemberInfo searchMemberInfo(Long memberId) {
		return memberService.getMemberInfo(memberId);
	}
}
