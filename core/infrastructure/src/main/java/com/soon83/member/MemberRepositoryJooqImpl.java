package com.soon83.member;

import static com.soon83.Tables.*;
import static com.soon83.utils.JooqUtil.*;

import java.util.List;
import java.util.Optional;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import com.soon83.utils.AssertUtil;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryJooqImpl implements MemberRepositoryJooq {
	private final DSLContext dsl;

	@Override
	public List<Member> getAllMemberList() {
		return dsl
			.selectFrom(MEMBER)
			.fetchInto(Member.class);
	}

	@Override
	public Optional<Member> getMember(Long memberId) {
		AssertUtil.notNull(memberId, "memberId");

		return Optional.ofNullable(dsl
			.selectFrom(MEMBER)
			.where(
				eq(MEMBER.MEMBER_ID, memberId)
			)
			.fetchOneInto(Member.class));
	}
}