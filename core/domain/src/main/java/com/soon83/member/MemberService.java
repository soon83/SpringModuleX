package com.soon83.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
	private final MemberMapper memberMapper;
	private final MemberReader memberReader;

	public Page<MemberInfo> searchPageMemberInfoList(MemberSearchCondition searchCondition, Pageable pageable) {
		return null;
	}

	public List<MemberInfo> getAllMemberInfoList(MemberSearchCondition searchCondition) {
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
