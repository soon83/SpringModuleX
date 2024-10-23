package com.soon83.member;

import com.soon83.tables.records.MemberRecord;

import java.util.List;
import java.util.Optional;

public interface MemberRepositoryJooq {
	List<MemberRecord> getAllMemberRecordList();

	Optional<MemberRecord> getMemberRecord(Long memberId);
}
