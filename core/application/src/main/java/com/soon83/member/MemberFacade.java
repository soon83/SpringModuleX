package com.soon83.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberFacade {
	private final MemberService memberService;

	public Page<MemberInfo> searchPageMemberInfoList(MemberSearchCondition searchCondition, Pageable pageable) {
		return memberService.searchPageMemberInfoList(searchCondition, pageable);
	}

	public List<MemberInfo> searchAllMemberInfoList(MemberSearchCondition searchCondition) {
		return memberService.getAllMemberInfoList(searchCondition);
	}

	public MemberInfo searchMemberInfo(Long memberId) {
		return memberService.getMemberInfo(memberId);
	}
}
