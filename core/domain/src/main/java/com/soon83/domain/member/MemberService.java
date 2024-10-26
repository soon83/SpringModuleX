package com.soon83.domain.member;

import com.soon83.dtos.member.*;
import com.soon83.dtos.utils.Sortable;
import com.soon83.entities.member.Member;
import com.soon83.infrastructure.member.MemberReader;
import com.soon83.infrastructure.member.MemberStore;
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
    private final MemberDomainMapper memberDomainMapper;
    private final MemberStore memberStore;
    private final MemberReader memberReader;

    /**
     * 회원 목록 조회 - 페이징
     */
    public Page<MemberInfo> getPageMemberInfoList(MemberSearchCondition searchCondition, Pageable pageable) {
        return memberReader.getPageMemberList(searchCondition, pageable)
                .map(memberDomainMapper::toMemberInfo);
    }

    /**
     * 회원 목록 조회 - 전체
     */
    public List<MemberInfo> getAllMemberInfoList(MemberSearchCondition searchCondition, Sortable sortable) {
        return memberReader.getAllMemberList(searchCondition, sortable).stream()
                .map(memberDomainMapper::toMemberInfo)
                .toList();
    }

    /**
     * 회원 단건 조회
     */
    public MemberInfo getMemberInfo(Long memberId) {
        Member member = memberReader.getMemberOrThrow(memberId);
        return memberDomainMapper.toMemberInfo(member);
    }

    /**
     * 회원 단건 등록
     */
    @Transactional
    public Long createMember(MemberCreateCommand command) {
        Member memberEntity = MemberFactory.createMember(command);
        Member createdMember = memberStore.create(memberEntity);
        return createdMember.getMemberId();
    }

    /**
     * 회원 대량 등록
     */
    @Transactional
    public void createBulkMember(MemberBulkCreateCommand bulkCommand) {
        bulkCommand.getCommandList().forEach(this::createMember);
    }

    /**
     * 회원 단건 수정
     */
    @Transactional
    public void updateMember(MemberUpdateCommand command) {
        Member member = memberReader.getMemberOrThrow(command.getMemberId());
        Member memberEntity = MemberFactory.updateMember(member, command);
        memberStore.update(memberEntity);
    }

    /**
     * 회원 대량 수정
     */
    @Transactional
    public void updateBulkMember(MemberBulkUpdateCommand bulkCommand) {
        bulkCommand.getCommandList().forEach(this::updateMember);
    }

    /**
     * 회원 단건 삭제
     */
    @Transactional
    public void deleteMember(MemberDeleteCommand command) {
        Member member = memberReader.getMemberOrThrow(command.getMemberId());
        memberStore.delete(member);
    }

    /**
     * 회원 대량 삭제
     */
    @Transactional
    public void deleteBulkMember(MemberBulkDeleteCommand bulkCommand) {
        bulkCommand.getCommandList().forEach(this::deleteMember);
    }
}
