package com.soon83.member;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/member-list")
@RequiredArgsConstructor
public class MemberController {
	private final MemberFacade memberFacade;

	@GetMapping("/{userId}")
	public MemberInfoResponse searchMemberInfo(@PathVariable Long userId) {
		log.debug("# searchUserInfo # userId: {}", userId);
		MemberInfo memberInfo = memberFacade.searchMemberInfo(userId);
		return new MemberInfoResponse(memberInfo);
	}
}
