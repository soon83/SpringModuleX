package com.soon83.member;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
	private final MemberMapper memberMapper;
	private final MemberReader memberReader;

	public List<MemberInfo> getAllMemberInfoList() {
		List<Member> memberList = memberReader.getAllMemberList();
		return memberList.stream()
			.map(memberMapper::toMemberInfo)
			.toList();
	}

	public MemberInfo getMemberInfo(Long memberId) {
		Member member = memberReader.getMemberOrThrow(memberId);
		return memberMapper.toMemberInfo(member);
	}
}
