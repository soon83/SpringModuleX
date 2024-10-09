package com.soon83.member;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
	private final MemberReader memberReader;

	public MemberInfo getMemberInfo(Long memberId) {
		Member member = memberReader.getMemberOrThrow(memberId);
		return new MemberInfo(member);
	}
}
