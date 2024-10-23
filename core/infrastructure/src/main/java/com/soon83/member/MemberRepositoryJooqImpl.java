package com.soon83.member;

import com.soon83.tables.records.MemberRecord;
import com.soon83.utils.AssertUtil;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.soon83.Tables.MEMBER;
import static com.soon83.utils.JooqUtil.eq;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryJooqImpl implements MemberRepositoryJooq {
	private final DSLContext dsl;

	@Override
	public List<MemberRecord> getAllMemberRecordList() {
		return dsl
			.selectFrom(MEMBER)
				.fetchInto(MemberRecord.class);
	}

	public Optional<MemberRecord> getMemberRecord(Long memberId) {
		AssertUtil.notNull(memberId, "memberId");

		return dsl
				.select(MEMBER.fields())
				.from(MEMBER)
				.where(
						eq(MEMBER.MEMBER_ID, memberId)
				)
				.fetchOptionalInto(MemberRecord.class);
	}
}