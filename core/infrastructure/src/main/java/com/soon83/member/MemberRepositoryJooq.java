package com.soon83.member;

import java.util.List;
import java.util.Optional;

public interface MemberRepositoryJooq {
	List<Member> getAllMemberList();

	Optional<Member> getMember(Long memberId);
}
