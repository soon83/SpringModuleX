package com.soon83.member;

import com.soon83.tables.records.MemberRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberReader {
    private final MemberRepository memberRepository;
    private final MemberEntityMapper memberEntityMapper;

    public Member getMemberOrThrow(Long memberId) {
        return memberRepository.getMemberRecord(memberId)
                .map(memberEntityMapper::toEntity)
                .orElseThrow(() -> new RuntimeException("조회된 회원이 없습니다."));
    }

    public Member getMemberOrNull(Long memberId) {
        return memberRepository.getMemberRecord(memberId)
                .map(memberEntityMapper::toEntity)
                .orElse(null);
    }

    public MemberRecord getMemberRecordOrThrow(Long memberId) {
        return memberRepository.getMemberRecord(memberId)
                .orElseThrow(() -> new RuntimeException("조회된 회원이 없습니다."));
    }

    public MemberRecord getMemberRecordOrNull(Long memberId) {
        return memberRepository.getMemberRecord(memberId)
                .orElse(null);
    }

    public List<Member> getAllMemberList() {
        return memberRepository.getAllMemberRecordList().stream()
                .map(memberEntityMapper::toEntity)
                .toList();
    }
}
